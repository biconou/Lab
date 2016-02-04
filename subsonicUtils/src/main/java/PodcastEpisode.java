import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by remi on 04/02/2016.
 */
@Entity(name = "PODCAST_EPISODE")
public class PodcastEpisode {

    @Id
    private int id;
    @Column(name = "CHANNEL_ID")
    private int channelId;
    @Column
    private String url;
    @Column
    private String path;
    @Column
    private String title;
    @Column
    private String description;
    @Column(name = "PUBLISH_DATE")
    private Date publishDate;
    @Column
    private String duration;
    @Column(name = "BYTES_TOTAL")
    private BigInteger bytesTotal;
    @Column(name = "BYTES_DOWNLOADED")
    private BigInteger bytesDownloaded;
    @Column
    private String status;
    @Column(name = "ERROR_MESSAGE")
    private String errorMessage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public BigInteger getBytesTotal() {
        return bytesTotal;
    }

    public void setBytesTotal(BigInteger bytesTotal) {
        this.bytesTotal = bytesTotal;
    }

    public BigInteger getBytesDownloaded() {
        return bytesDownloaded;
    }

    public void setBytesDownloaded(BigInteger bytesDownloaded) {
        this.bytesDownloaded = bytesDownloaded;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
