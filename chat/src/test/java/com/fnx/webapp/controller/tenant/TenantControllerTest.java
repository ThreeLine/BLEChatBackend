package com.fnx.webapp.controller.tenant;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.fnx.webapp.controller.BaseControllerTestCase;

public class TenantControllerTest extends BaseControllerTestCase {
	
	@Test
	public void loadTenants() throws Exception {

	    HttpClient httpclient = new DefaultHttpClient();
	    httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

	    HttpPost httppost = new HttpPost("http://127.0.0.1:8080/chat/open/image/upload.json");
	    File file = new File("/Users/oceanyang/purple_button.png");

	    MultipartEntity mpEntity = new MultipartEntity();
	    ContentBody cbFile = new FileBody(file, "image/jpeg");
	    mpEntity.addPart("imgFile", cbFile);


	    httppost.setEntity(mpEntity);
	    System.out.println("executing request " + httppost.getRequestLine());
	    HttpResponse response = httpclient.execute(httppost);
	    HttpEntity resEntity = response.getEntity();

	    System.out.println(response.getStatusLine());
	    if (resEntity != null) {
	      System.out.println(EntityUtils.toString(resEntity));
	    }
	    if (resEntity != null) {
	      resEntity.consumeContent();
	    }

	    httpclient.getConnectionManager().shutdown();
		
	}
}
