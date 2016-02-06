import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;

/**
 * Created by remi on 03/02/2016.
 */
@Entity(name = "PODCAST_CHANNEL")
public class PodcastChannel {

    @Id
    private int id;
    @Column
    private String url;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String status;
    @Column(name = "ERROR_MESSAGE")
    private String errorMessage;

    @OneToMany(mappedBy="channel",targetEntity=PodcastEpisode.class)
    Collection<PodcastEpisode> episodes = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Collection<PodcastEpisode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Collection<PodcastEpisode> episodes) {
        this.episodes = episodes;
    }
}
