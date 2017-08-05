package ncm.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by wenxiangzhou214164 on 2017/7/29.
 */
@Entity
@Table(name = "ncm_comment")
public class NCMCommentModel implements Serializable {

    private static final long serialVersionUID = 8443759640642751271L;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Long songId;

    private String comment;

    private Date date;

    private Integer praise;

    private String ori;

    private Long oriUserId;

    private String oriUserName;

    private String reply;

    private Long replyUserId;

    private String replyUserName;

    @Column(insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatetime;

    @Column(insertable = false, updatable = false,
            columnDefinition = "CURRENT_TIMESTAMP")
    private Timestamp createtime;

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Long getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(Long replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getReplyUserName() {
        return replyUserName;
    }

    public void setReplyUserName(String replyUserName) {
        this.replyUserName = replyUserName;
    }

    public String getOri() {
        return ori;
    }

    public void setOri(String ori) {
        this.ori = ori;
    }

    public Long getOriUserId() {
        return oriUserId;
    }

    public void setOriUserId(Long oriUserId) {
        this.oriUserId = oriUserId;
    }

    public String getOriUserName() {
        return oriUserName;
    }

    public void setOriUserName(String oriUserName) {
        this.oriUserName = oriUserName;
    }

    @Override
    public String toString() {
        return "NCMCommentModel{" +
                "name='" + name + '\'' +
                ", songId=" + songId +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                ", praise=" + praise +
                ", ori='" + ori + '\'' +
                ", oriUserId=" + oriUserId +
                ", oriUserName='" + oriUserName + '\'' +
                ", reply='" + reply + '\'' +
                ", replyUserId=" + replyUserId +
                ", replyUserName='" + replyUserName + '\'' +
                ", updatetime=" + updatetime +
                ", createtime=" + createtime +
                '}';
    }
}
