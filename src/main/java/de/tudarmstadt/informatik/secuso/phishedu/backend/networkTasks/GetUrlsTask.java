//package de.tudarmstadt.informatik.secuso.phishedu.backend.networkTasks;
//
//import java.io.ByteArrayOutputStream;
//
//import java.io.IOException;
//import java.util.Arrays;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.StatusLine;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonSyntaxException;
//
//import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishAttackType;
//import de.tudarmstadt.informatik.secuso.phishedu.backend.BasePhishURL;
//import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishURL;
//
//import android.os.AsyncTask;
//
///**
// * This Task gets URLs from the backend server.
// * @author Clemens Bergmann <cbergmann@schuhklassert.de>
// *
// */
//public class GetUrlsTask extends AsyncTask<Integer, Integer, PhishURL[]>{
//	private UrlsLoadedListener controller;
//	private PhishAttackType type = PhishAttackType.NoPhish;
//
//	/**
//	 * This is the main constructor
//	 * @param controller The Controller that will be notified when download is finished.
//	 */
//	public GetUrlsTask(UrlsLoadedListener controller){
//		this.controller=controller;
//	}
//
//	protected PhishURL[] doInBackground(Integer... params) {
//		int count = params[0];
//		this.type = PhishAttackType.NoPhish;
//		for(PhishAttackType type : PhishAttackType.values()){
//			if(type.getValue() == params[1]){
//				this.type=type;
//				break;
//			}
//		}
//		try {
//			String url = "https://api.no-phish.de/urls/"+this.type.toString()+".json";
//			HttpResponse response = new DefaultHttpClient().execute(new HttpGet(url));
//			StatusLine statusLine = response.getStatusLine();
//			if(statusLine.getStatusCode() == HttpStatus.SC_OK){
//				ByteArrayOutputStream out = new ByteArrayOutputStream();
//				response.getEntity().writeTo(out);
//				out.close();
//				BasePhishURL[] result = new BasePhishURL[0];
//				try {
//					result = (new Gson()).fromJson(out.toString(), BasePhishURL[].class);
//					for (BasePhishURL newurl : result) {
//						newurl.validateProviderName();
//					}
//				} catch (JsonSyntaxException e) {
//					// TODO: handle exception
//				}
//				if(result.length > count){
//					result = Arrays.copyOf(result, count);
//				}
//				return result;
//			} else{
//				//Closes the connection.
//				response.getEntity().getContent().close();
//				throw new IOException(statusLine.getReasonPhrase());
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return new PhishURL[0];
//	}
//
//	protected void onProgressUpdate(Integer... progress) {
//		this.controller.urlDownloadProgress(progress[0]);
//	}
//
//	@Override
//	protected void onPostExecute(PhishURL[] result) {
//		this.controller.urlsReturned(result, this.type);
//	}
//}
