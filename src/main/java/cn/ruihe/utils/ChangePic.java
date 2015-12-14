package cn.ruihe.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by xuhui on 15-12-9.
 */
public class ChangePic {


    private OssUploadUtil ossUploadUtil = new OssUploadUtil();


    private String picPath = "";

    private String smallPicPath = "";


    public void doOper() {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;


        try {
            pstmt = conn.prepareStatement("select * from \"cliReadilyTakePost\"");
            rs = pstmt.executeQuery();
            String newUrl = "";
            String newSmallUrl = "";
            while (rs.next()) {
                String img = rs.getString("\"pictures\"");
                String smallImg = rs.getString("\"smallpic\"");
                int id = rs.getInt("\"id\"");
                if (img != null) {
                    if (img.contains(";")) {
                        String[] ss = img.split(";");
                        for (String s : ss) {
                            String url = ossUploadUtil.uploadFile(picPath + s);
                            newUrl += url + ";";
                        }
                    }
                }

                if (smallImg != null) {
                    if (smallImg.contains(";")) {
                        String[] ss = img.split(";");
                        for (String s : ss) {
                            String url = ossUploadUtil.uploadFile(smallPicPath + s);
                            newSmallUrl += url + ";";
                        }
                    }
                }


                Connection conn2 = null;
                PreparedStatement pstmt2 = null;
                try {
                    pstmt2 = conn2.prepareStatement("update \"cliReadilyTakePost\" set \"pictures=\"" + img + ",\"smallpic\"=" + newSmallUrl + " where \"id\"=" + id);
                    pstmt2.execute();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } finally {
                    if (pstmt2 != null) {
                        pstmt2.close();
                    }
                    if (conn2 != null) {
                        conn2.close();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


//    public static void main(String[] args)
}
