package bookshop.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class QnaDBBean {
	
    private static QnaDBBean instance = new QnaDBBean();
	
    //.jsp���������� DB�������� BoardDBBeanŬ������ �޼ҵ忡 ���ٽ� �ʿ�
    public static QnaDBBean getInstance() {
        return instance;
    }
    
    private QnaDBBean(){}
    
    //Ŀ�ؼ�Ǯ�κ��� Connection��ü�� ��
    private Connection getConnection() throws Exception {
      Context initCtx = new InitialContext();
      Context envCtx = (Context) initCtx.lookup("java:comp/env");
      DataSource ds = (DataSource)envCtx.lookup("jdbc/jsptest");
      return ds.getConnection();
    }
    
    //qna���̺� ���� �߰� - ����ڰ� �ۼ��ϴ� ��
    @SuppressWarnings("resource")
	public int insertArticle(QnaDataBean article){
        Connection conn = null;
        PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
        String sql="";
        int group_id = 1;        
        try {
            conn = getConnection();
            
            pstmt = conn.prepareStatement("select max(qna_id) from qna");
            rs = pstmt.executeQuery();
            
            if (rs.next()) 
                x= rs.getInt(1);
            
            if(x > 0)
               group_id = rs.getInt(1)+1;
                   	
            // ������ �ۼ� :board���̺� ���ο� ���ڵ� �߰�
            sql = "insert into qna(book_id,book_title,qna_writer,qna_content,";
		    sql += "group_id,qora,reply,reg_date) values(?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, article.getBook_id());
            pstmt.setString(2, article.getBook_title());
            pstmt.setString(3, article.getQna_writer());
            pstmt.setString(4, article.getQna_content());
            pstmt.setInt(5, group_id);
            pstmt.setInt(6, article.getQora());
            pstmt.setInt(7, article.getReply());
			pstmt.setTimestamp(8, article.getReg_date());
            pstmt.executeUpdate();
            
            x = 1; //���ڵ� �߰� ����
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
			if (rs != null) try{ rs.close(); }catch(SQLException ex) {}
            if (pstmt != null) try{ pstmt.close(); }catch(SQLException ex) {}
            if (conn != null) try{ conn.close(); }catch(SQLException ex) {}
        }
        return x;
    }
    
    //qna���̺� ���� �߰� - �����ڰ� �ۼ��� �亯
    @SuppressWarnings("resource")
	public int insertArticle(QnaDataBean article, int qna_id){
        Connection conn = null;
        PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
        String sql="";
        
        try {
            conn = getConnection();
            
            // ������ �ۼ� :board���̺� ���ο� ���ڵ� �߰�
            sql = "insert into qna(book_id,book_title,qna_writer,qna_content,";
		    sql += "group_id,qora,reply,reg_date) values(?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, article.getBook_id());
            pstmt.setString(2, article.getBook_title());
            pstmt.setString(3, article.getQna_writer());
            pstmt.setString(4, article.getQna_content());
            pstmt.setInt(5, article.getGroup_id());
            pstmt.setInt(6, article.getQora());
            pstmt.setInt(7, article.getReply());
			pstmt.setTimestamp(8, article.getReg_date());
            pstmt.executeUpdate();
            
            sql="update qna set reply=? where qna_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 1);
		    pstmt.setInt(2, qna_id);
            pstmt.executeUpdate();
            
            x = 1; //���ڵ� �߰� ����
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
			if (rs != null) try{ rs.close(); }catch(SQLException ex) {}
            if (pstmt != null) try{ pstmt.close(); }catch(SQLException ex) {}
            if (conn != null) try{ conn.close(); }catch(SQLException ex) {}
        }
        return x;
    }
    
    //qna���̺� ����� ��ü���� ���� ��
	public int getArticleCount(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int x=0;

        try {
            conn = getConnection();
            
            pstmt = conn.prepareStatement("select count(*) from qna");
            rs = pstmt.executeQuery();

            if (rs.next()) 
               x= rs.getInt(1);
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) try{ rs.close(); }catch(SQLException ex) {}
            if (pstmt != null) try{ pstmt.close(); }catch(SQLException ex) {}
            if (conn != null) try{ conn.close(); }catch(SQLException ex) {}
        }
		return x;
    }
	
	//Ư�� å�� ���� �ۼ��� qna���� ���� ��
	public int getArticleCount(int book_id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int x=0;

        try {
            conn = getConnection();
            
            pstmt = conn.prepareStatement("select count(*) from qna where book_id = "+book_id);
            rs = pstmt.executeQuery();

            if (rs.next()) 
               x= rs.getInt(1);
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) try{ rs.close(); }catch(SQLException ex) {}
            if (pstmt != null) try{ pstmt.close(); }catch(SQLException ex) {}
            if (conn != null) try{ conn.close(); }catch(SQLException ex) {}
        }
		return x;
    }
	
		
    //������ ���� �ش��ϴ� qna���� ���� ��
	public List<QnaDataBean> getArticles(int count){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<QnaDataBean> articleList=null;//�۸���� �����ϴ� ��ü
        try {
            conn = getConnection();
            
            pstmt = conn.prepareStatement(
            	"select * from qna order by group_id desc, qora asc");
            
            rs = pstmt.executeQuery();

            if (rs.next()) {//ResultSet�� ���ڵ带 ����
                articleList = new ArrayList<QnaDataBean>(count);
                do{
                  QnaDataBean article= new QnaDataBean();
				  article.setQna_id(rs.getInt("qna_id")); 
				  article.setBook_id(rs.getInt("book_id"));
				  article.setBook_title(rs.getString("book_title"));
                  article.setQna_writer(rs.getString("qna_writer"));
                  article.setQna_content(rs.getString("qna_content"));
                  article.setGroup_id(rs.getInt("group_id"));
                  article.setQora(rs.getByte("qora"));
                  article.setReply(rs.getByte("reply"));
			      article.setReg_date(rs.getTimestamp("reg_date"));

				  //List��ü�� ������������� BoardDataBean��ü�� ����
                  articleList.add(article);
			    }while(rs.next());
			}
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
		return articleList;//List��ü�� ���۷����� ����
    }
	
	//Ư�� å�� ���� �ۼ��� qna���� ������ �� ��ŭ ��
	public List<QnaDataBean> getArticles(int count, int book_id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<QnaDataBean> articleList=null;//�۸���� �����ϴ� ��ü
        try {
            conn = getConnection();
           
            pstmt = conn.prepareStatement(
            	"select * from qna where book_id="+book_id+" order by group_id desc, qora asc");
            
            rs = pstmt.executeQuery();

            if (rs.next()) {//ResultSet�� ���ڵ带 ����
                articleList = new ArrayList<QnaDataBean>(count);
                do{
                  QnaDataBean article= new QnaDataBean();
				  article.setQna_id(rs.getInt("qna_id")); 
				  article.setBook_id(rs.getInt("book_id"));
				  article.setBook_title(rs.getString("book_title"));
                  article.setQna_writer(rs.getString("qna_writer"));
                  article.setQna_content(rs.getString("qna_content"));
                  article.setGroup_id(rs.getInt("group_id"));
                  article.setQora(rs.getByte("qora"));
                  article.setReply(rs.getByte("reply"));
			      article.setReg_date(rs.getTimestamp("reg_date"));

				  //List��ü�� ������������� BoardDataBean��ü�� ����
                  articleList.add(article);
			    }while(rs.next());
			}
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
		return articleList;//List��ü�� ���۷����� ����
    }
	
    //QnA�� ���������� ����� ���� ����
    public QnaDataBean updateGetArticle(int qna_id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        QnaDataBean article=null;
        try {
            conn = getConnection();

            pstmt = conn.prepareStatement(
            	"select * from qna where qna_id = ?");
            pstmt.setInt(1, qna_id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                article = new QnaDataBean();
                article.setQna_id(rs.getInt("qna_id")); 
				article.setBook_id(rs.getInt("book_id"));
				article.setBook_title(rs.getString("book_title"));
                article.setQna_writer(rs.getString("qna_writer"));
                article.setQna_content(rs.getString("qna_content"));
                article.setGroup_id(rs.getInt("group_id"));
                article.setQora(rs.getByte("qora"));
                article.setReply(rs.getByte("reply"));
			    article.setReg_date(rs.getTimestamp("reg_date"));     
			}
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) try{rs.close();}catch(SQLException ex){}
            if (pstmt != null) try{pstmt.close();}catch(SQLException ex){}
            if (conn != null) try{conn.close();}catch(SQLException ex){}
        }
		return article;
    }
    
    //QnA�� ���� ����ó������ ���
	public int updateArticle(QnaDataBean article){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs= null;
		int x=-1;
        try {
            conn = getConnection();
            
            String sql="update qna set qna_content=? where qna_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, article.getQna_content());
			pstmt.setInt(2, article.getQna_id());
            pstmt.executeUpdate();
			x= 1;
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
			if (rs != null) try{ rs.close(); }catch(SQLException ex) {}
            if (pstmt != null) try{ pstmt.close(); }catch(SQLException ex) {}
            if (conn != null) try{ conn.close(); }catch(SQLException ex) {}
        }
		return x;
    }
    
    //QnA�� ��������ó���� ���
	public int deleteArticle(int qna_id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs= null;
        int x=-1;
        try {
			conn = getConnection();

            pstmt = conn.prepareStatement(
            	      "delete from qna where qna_id=?");
            pstmt.setInt(1, qna_id);
            pstmt.executeUpdate();
			x= 1; //�ۻ��� ����
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) try{ rs.close(); }catch(SQLException ex) {}
            if (pstmt != null) try{ pstmt.close(); }catch(SQLException ex) {}
            if (conn != null) try{ conn.close(); }catch(SQLException ex) {}
        }
		return x;
    }
}