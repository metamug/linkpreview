package net.metamug.metascrapper.entity;

/**
 *
 * @author deepak
 */
public class MetaImage {

    private static final long serialVersionUID = 1L;
    private String id;
    private String url;
    private Short width;
    private Short height;

    public MetaImage() {
    }

    public MetaImage(String imageId) {
        this.id = imageId;
    }

    public String getId() {
        return id;
    }

    public void setId(String imageId) {
        this.id = imageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String imageUrl) {
        this.url = imageUrl;
    }

    public Short getWidth() {
        return width;
    }

    public void setWidth(Short width) {
        this.width = width;
    }

    public Short getHeight() {
        return height;
    }

    public void setHeight(Short height) {
        this.height = height;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MetaImage)) {
            return false;
        }
        MetaImage other = (MetaImage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.metamug.dao2.ImageMap[ imageId=" + id + " ]";
    }
}
