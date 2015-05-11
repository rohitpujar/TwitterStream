import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class SearchTweets {

    static Connection conn;

    public static void main(String[] args) throws SQLException {

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("Q1kDkGBTtZJTFAgOE5YFcD8MU");
        cb.setOAuthConsumerSecret("sLeyuPa97xyHICEFLCj0PZjUbR3Pb2jpY2wwEOB60OMnVq23Kp");
        cb.setOAuthAccessToken("2287489038-SxNAoyiCcn3Z7evfCFGKNCKy5zriiG4F8Cuwkvy");
        cb.setOAuthAccessTokenSecret("4njaurDQDoDM5SUtMU3C4jzgieDoWSwWBMBM2V4ngBcFA");

        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        StatusListener listener = new StatusListener() {

            @Override
            public void onException(Exception arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrubGeo(long arg0, long arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStatus(Status status) {
            	System.out.println("---------------------------------------------------------------------------------------");
                User user = status.getUser();
//                FilterQuery tweetFilterQuery = new FilterQuery();
                
                // gets Username
                String username = status.getUser().getScreenName();
                System.out.println("Username : "+username);
                String profileLocation = user.getLocation();
                System.out.println("Location : "+profileLocation);
                long tweetId = status.getId();
                System.out.println(tweetId);
                String content = status.getText();
                content = content.replaceAll("[-+^:,@]","");
                System.out.println("Tweet : "+content);
                
                try {
					Util.writeStringToFile(tweetId+","+username.toString()+","+profileLocation+","+content);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                /*try {
                    conn = DriverManager.getConnection("jdbc:mysql://localhost", "root", "root");
                    Statement stmt = conn.createStatement();
                    stmt.executeQuery("use twitter;");
                    stmt.executeUpdate("INSERT INTO tweets VALUES(\"" + username + "\",\"" + profileLocation + "\",\"" + tweetId + "\",\"" + content + "\");");
                } catch (SQLException ex) {
                    Logger.getLogger(SearchTweets.class.getName()).log(Level.SEVERE, null, ex);
                }*/

            }

            @Override
            public void onTrackLimitationNotice(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStallWarning(StallWarning sw) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        };
        FilterQuery fq = new FilterQuery();

        String keywords[] = {"#tweetforutd"};

        fq.track(keywords);
        fq.language(new String[]{"en"});
        twitterStream.addListener(listener);
        twitterStream.filter(fq);

    }
}