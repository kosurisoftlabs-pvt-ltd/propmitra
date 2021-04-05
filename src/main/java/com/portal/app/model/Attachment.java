package com.portal.app.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "prop_attachment")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROP_ATCH_PKID")
    private Long id;

    @Column(name = "PROP_ATCH_NAME")
    private String fileName;

    @Column(name = "PROP_ATCH_TYPE")
    private String fileType;

    @Lob
    @Column(name = "PROP_ATCH_DATA")
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "PROP_FLOOR_PKID", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PropertyFloor propertyFloor;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "PROP_GAL_PKID", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PropertyGallery propertyGallery;

    @OneToOne(mappedBy = "frontImage")
    private PropertyBasic propertyBasic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public PropertyFloor getPropertyFloor() {
        return propertyFloor;
    }

    public void setPropertyFloor(PropertyFloor propertyFloor) {
        this.propertyFloor = propertyFloor;
    }

    public PropertyGallery getPropertyGallery() {
        return propertyGallery;
    }

    public void setPropertyGallery(PropertyGallery propertyGallery) {
        this.propertyGallery = propertyGallery;
    }

    public PropertyBasic getPropertyBasic() {
        return propertyBasic;
    }

    public void setPropertyBasic(PropertyBasic propertyBasic) {
        this.propertyBasic = propertyBasic;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
