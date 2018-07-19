package collectortest.sina;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.DefaultCookieSpec;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class SinaWeiboLogger {
	private HttpClient client;
	private HttpPost post;
	private HttpGet get;
	private BasicCookieStore cookieStore;

	private String username; // 明文账号
	private String password; // 明文密码
	private String su; // base64加密后账号
	private String sp; // RSA加密后密码

	// 预登陆信息 所需数据 开始
	private long servertime; // 服务器时间戳
	private String nonce; // 服务器返回字符串
	private String rsakv; // 服务器返回字符串，rsa加密密码用
	private String pubkey; // 服务器返回rsa加密公用秘钥，用于rsa加密
	private int retcode; // 状态值
	private String pcid;
	private int is_openlock;
	private int showpin;
	private int exectime;
	// 预登陆信息 结束

	// 登录后返回json数据
	private String uid;
	private String nick;
	private JSONArray domainurls;
	//

	private StringBuffer headerCookie = new StringBuffer();

	// 登录密码加密js文件内容
	private static String sina_js = SinaJS.getJS();

	public SinaWeiboLogger(String username, String password) {
		this.username = username;
		this.password = password;

		// cookie策略，不设置会拒绝cookie rejected，设置策略保存cookie信息
		cookieStore = new BasicCookieStore();
		CookieSpecProvider myCookie = new CookieSpecProvider() {

			public CookieSpec create(HttpContext context) {
				return new DefaultCookieSpec();
			}
		};
		Registry<CookieSpecProvider> rg = RegistryBuilder.<CookieSpecProvider> create().register("myCookie", myCookie)
				.build();

		client = HttpClients.custom().setDefaultCookieStore(cookieStore).setDefaultCookieSpecRegistry(rg).build();
		get = new HttpGet();
		post = new HttpPost();
	}

	private void preLogin() throws IOException, URISyntaxException {
		// 1
		su = new String(Base64.encodeBase64(URLEncoder.encode(this.username, "UTF-8").getBytes()));
		String preLoginUrl = "http://login.sina.com.cn/sso/prelogin.php?entry=account&callback=sinaSSOController.preloginCallBack&su="
				+ su + "&rsakt=mod&client=ssologin.js(v1.4.15)&_=" + new Date().getTime();
		get.setURI(new URI(preLoginUrl));
		get.addHeader("Host", "login.sina.com.cn");
		get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
		get.addHeader("Accept", "*/*");
		get.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		get.addHeader("Accept-Encoding", "gzip,deflate");
		get.addHeader("Referer", "http://login.sina.com.cn/");
		get.addHeader("Connection", "keep-alive");

		HttpResponse resp = client.execute(get);
		// 保存请求返回cookie，登录后请求其他页面需要带cookie
		saveCookie(resp.getAllHeaders(), this.headerCookie);

		HttpEntity entity = resp.getEntity();
		String cont = EntityUtils.toString(entity);
		// System.out.println(cont);
		cont = cont.replace("sinaSSOController.preloginCallBack(", "");
		cont = cont.replace(")", "");
		JSONObject json = JSONObject.parseObject(cont);
		this.retcode = json.getIntValue("retcode");
		this.servertime = json.getLongValue("servertime");
		this.pcid = json.getString("pcid");
		this.nonce = json.getString("nonce");
		this.pubkey = json.getString("pubkey");
		this.rsakv = json.getString("rsakv");
		this.is_openlock = json.getIntValue("is_openlock");
		this.showpin = json.getIntValue("showpin");
		this.exectime = json.getIntValue("exectime");
	}

	private void saveCookie(Header[] headers, StringBuffer headerCookie) {
		for (Header h : headers) {
			if (h.getName().equals("Set-Cookie")) {
				headerCookie.append(h.getValue()).append(";");
			}
		}
	}

	private void accountLogin()
			throws NoSuchMethodException, ScriptException, ClientProtocolException, IOException, URISyntaxException {
		// 2
		String loginUrl = "http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.15)&_="
				+ new Date().getTime();
		post.setURI(new URI(loginUrl));
		post.addHeader("Host", "login.sina.com.cn");
		post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
		post.addHeader("Accept", "*/*");
		post.addHeader("Content-Type", "application/x-www-form-urlencoded");
		post.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		post.addHeader("Accept-Encoding", "gzip, deflate");
		post.addHeader("Referer", "http://login.sina.com.cn/");
		post.addHeader("Connection", "keep-alive");

		// 使用js加密密码,RSA,调用js内方法
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine se = sem.getEngineByName("javascript");

		se.eval(sina_js);

		if (se instanceof Invocable) {
			Invocable iv = (Invocable) se;
			sp = (String) iv.invokeFunction("getpass", this.password, this.servertime, this.nonce, this.pubkey);
		}

		List<NameValuePair> parms = new ArrayList<NameValuePair>();
		parms.add(new BasicNameValuePair("entry", "account"));
		parms.add(new BasicNameValuePair("gateway", "1"));
		parms.add(new BasicNameValuePair("from", ""));
		parms.add(new BasicNameValuePair("savestate", "0"));
		parms.add(new BasicNameValuePair("useticket", "0"));
		parms.add(new BasicNameValuePair("pagerefer", ""));
		parms.add(new BasicNameValuePair("vsnf", "1"));
		parms.add(new BasicNameValuePair("su", su));
		parms.add(new BasicNameValuePair("service", "sso"));
		parms.add(new BasicNameValuePair("servertime", servertime + ""));
		parms.add(new BasicNameValuePair("nonce", nonce));
		parms.add(new BasicNameValuePair("pwencode", "rsa2"));
		parms.add(new BasicNameValuePair("rsakv", rsakv));
		parms.add(new BasicNameValuePair("sp", sp));
		parms.add(new BasicNameValuePair("sr", "1366*768"));
		parms.add(new BasicNameValuePair("encoding", "UTF-8"));
		parms.add(new BasicNameValuePair("cdult", "3"));
		parms.add(new BasicNameValuePair("domain", "sina.com.cn"));
		parms.add(new BasicNameValuePair("prelt", "51"));
		parms.add(new BasicNameValuePair("returntype", "TEXT"));
		post.setEntity(new UrlEncodedFormEntity(parms));

		HttpResponse resp = client.execute(post);

		// 保存请求返回cookie，登录后请求其他页面需要带cookie
		saveCookie(resp.getAllHeaders(), this.headerCookie);

		// 请求返回链接
		JSONObject jsonObj = JSONObject.parseObject(EntityUtils.toString(resp.getEntity()));
		System.out.println(jsonObj.toJSONString());
		this.retcode = jsonObj.getIntValue("retcode");
		this.uid = jsonObj.getString("uid");
		this.nick = jsonObj.getString("nick");
		this.domainurls = jsonObj.getJSONArray("crossDomainUrlList");
		List<String> retMsg = new ArrayList<String>();
		int i = 0;
		// 上面的登录是登录新浪通信证，下面这个crossDomain是跨域登录微博，微财富，97973网站，sso统一登录
		for (Object url : domainurls) {
			get.setURI(new URI(url.toString() + "&callback=sinaSSOController.doCrossDomainCallBack&scriptId=ssoscript"
					+ i + "&client=ssologin.js(v1.4.15)&_=" + new Date().getTime()));

			resp = client.execute(get);

			retMsg.add(EntityUtils.toString(resp.getEntity()));
			i++;
		}
		get.setURI(new URI("http://login.sina.com.cn/member/my.php?entry=sso"));

		// 添加请求cookie到请求头
		get.addHeader("Cookie", this.headerCookie.toString());

		resp = client.execute(get);

		String cont = readStreamByEncoding(resp.getEntity().getContent(), "GBK");
		retMsg.add(cont);

		for (String s : retMsg) {
			System.out.println(s);
		}
	}

	private String readStreamByEncoding(InputStream in, String encoding) throws IOException {
		StringBuffer cont = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(in, encoding));
		String tmp = "";
		while ((tmp = br.readLine()) != null) {
			cont.append(tmp);
		}
		br.close();
		return cont.toString();
	}

	/**
	 * 登录
	 */
	public void login() {
		try {
			this.preLogin();
			this.accountLogin();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 主函数
	 * @param args
	 */
	public static void main(String[] args) {
		new SinaWeiboLogger("Fighting", "zj").login();
	}
	
	public HttpClient getClient() {
		return client;
	}

	public void setClient(HttpClient client) {
		this.client = client;
	}

	public HttpPost getPost() {
		return post;
	}

	public void setPost(HttpPost post) {
		this.post = post;
	}

	public HttpGet getGet() {
		return get;
	}

	public void setGet(HttpGet get) {
		this.get = get;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSu() {
		return su;
	}

	public void setSu(String su) {
		this.su = su;
	}

	public String getSp() {
		return sp;
	}

	public void setSp(String sp) {
		this.sp = sp;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getRsakv() {
		return rsakv;
	}

	public void setRsakv(String rsakv) {
		this.rsakv = rsakv;
	}

	public String getPubkey() {
		return pubkey;
	}

	public void setPubkey(String pubkey) {
		this.pubkey = pubkey;
	}

	public int getRetcode() {
		return retcode;
	}

	public void setRetcode(int retcode) {
		this.retcode = retcode;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public static String getSina_js() {
		return sina_js;
	}

	public BasicCookieStore getCookieStore() {
		return cookieStore;
	}

	public void setCookieStore(BasicCookieStore cookieStore) {
		this.cookieStore = cookieStore;
	}

}