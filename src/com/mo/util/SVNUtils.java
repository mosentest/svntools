package com.mo.util;

import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * http://www.myexception.cn/cvs-svn/1518736.html
 * Created by moziqi on 2015/12/15.
 */
public class SVNUtils implements ISVNLogEntryHandler {

    private static SVNUtils svnUtils = null;

    private String svnUrl = "";
    private String username = "";
    private String password = "";
    private String beginTime = "";
    private String endTime = "";
    private int startRevision = 0;
    private int endRevision = -1;//最后的一般版本号
    private String commitContent = "";
    private String author = "";//查询谁
    private static SVNRepository repository = null;
    private List<String> history; //日志list集合

    public static SVNUtils getInstance() {
        synchronized (SVNUtils.class) {
            if (svnUtils == null) {
                svnUtils = new SVNUtils();
            }
            return svnUtils;
        }
    }

    @Override
    public void handleLogEntry(SVNLogEntry svnLogEntry) throws SVNException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date begin = null;
        Date end = null;
        try {
            begin = format.parse(beginTime);
            end = format.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //比较时间
        if (svnLogEntry.getDate().after(begin) && svnLogEntry.getDate().before(end)) {
            // 比较用户和提交内容
            if (!"".equals(author)) {
                if (author.equals(svnLogEntry.getAuthor())) {
                    saveLog(svnLogEntry);
                }
            } else if (!"".equals(commitContent)) {
                if (commitContent.equals(svnLogEntry.getMessage())) {
                    saveLog(svnLogEntry);
                }
            } else {
                saveLog(svnLogEntry);
            }
        }
    }

    @SuppressWarnings("unchecked")
	private void saveLog(SVNLogEntry svnLogEntry) {
        history.addAll(svnLogEntry.getChangedPaths().keySet());
    }

    /**
     * @param svnUrl
     * @param username
     * @param password
     */
    public SVNUtils init(String svnUrl, String username, String password) {
        history = new ArrayList<String>();
        if (!"".equals(svnUrl)) {
            this.svnUrl = svnUrl;
        }
        if (!"".equals(username)) {
            this.username = username;
        }
        if (!"".equals(password)) {
            this.password = password;
        }
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
        try {
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(this.svnUrl));
        } catch (SVNException e) {
        	JOptionPane.showMessageDialog(null, e.getMessage(), "登录...",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(this.username, this.password);      
        repository.setAuthenticationManager(authManager);
        return this;
    }

    public SVNUtils init() {
        return init(svnUrl, username, password);
    }

    /**
     * @param commitContent
     * @param beginTime
     * @param endTime
     * @param startRevision
     * @param endRevision
     * @param author
     * @return
     */
    public List<String> log(String commitContent, String beginTime, String endTime, int startRevision, int endRevision, String author) {
        if (!"".equals(commitContent)) {
            this.commitContent = commitContent;
        }
        if (!"".equals(beginTime)) {
            this.beginTime = beginTime;
        }
        if (!"".equals(endTime)) {
            this.endTime = endTime;
        }
        if (beginTime.compareTo(endTime) >= 0) {
            throw new RuntimeException("beginTime < endTime ? ");
        }
        if (startRevision <= 0) {
            this.startRevision = 0;
        } else {
            this.startRevision = startRevision;
        }
        if (endRevision <= -1 || endRevision == 0) {
            this.endRevision = -1;
        } else {
            this.endRevision = endRevision;
        }
        if (startRevision > (endRevision == -1 ? startRevision + 1 : endRevision)) {
            throw new RuntimeException("startRevision < endRevision ? ");
        }
        if (!"".equals(author)) {
            this.author = author;
        }
        try {
        	if(history == null){
        		history = new ArrayList<String>();
        	}
            if (!history.isEmpty()) {
                history.clear();
            }
            //String[] 为过滤的文件路径前缀，为空表示不进行过滤
            repository.log(new String[]{""}, this.startRevision, this.endRevision, true, true, this);
        } catch (SVNException e) {
        	JOptionPane.showMessageDialog(null, e.getMessage(), "登录...",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return history;
    }

}
