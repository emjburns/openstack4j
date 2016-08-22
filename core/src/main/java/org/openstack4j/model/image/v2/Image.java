package org.openstack4j.model.image.v2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.omg.PortableInterceptor.ACTIVE;
import org.openstack4j.common.Buildable;
import org.openstack4j.model.common.BasicResource;
import org.openstack4j.model.image.v2.builder.ImageBuilder;

import java.util.Date;
import java.util.List;

import static com.sun.jmx.snmp.EnumRowStatus.active;
import static sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte1.other;

/**
 * A Glance v2.0-2.3 Image
 * @author emjburns
 * @see http://developer.openstack.org/api-ref-image-v2.html#showImage-v2
 */
public interface Image extends BasicResource, Buildable<ImageBuilder> {

    public enum Status {
        /**
         * Image status is not one of the documented options.
         * http://docs.openstack.org/developer/glance/statuses.html
         */
        UNRECOGNIZED,
        /**
         * The image identifier has been reserved for an image in the Glance registry.
         * No image data has been uploaded to Glance and the image size
         * was not explicitly set to zero on creation.
         */
        QUEUED,
        /**
         * Denotes that an image’s raw data is currently being uploaded to Glance.
         * When an image is registered with a call to POST /images and there is an
         * x-image-meta-location header present, that image will never be in the saving status
         * (as the image data is already available in some other location).
         */
        SAVING,
        /**
         * Denotes an image that is fully available in Glance. This occurs when the
         * image data is uploaded, or the image size is explicitly set to zero on creation.
         */
        ACTIVE,
        /**
         * Denotes that access to image data is not allowed to any non-admin user.
         * Prohibiting downloads of an image also prohibits operations like image
         * export and image cloning that may require image data.
         */
        DEACTIVATED,
        /**
         * Denotes that an error occurred during the uploading of an image’s data,
         * and that the image is not readable.
         */
        KILLED,
        /**
         * Glance has retained the information about the image, but it is no longer
         * available to use. An image in this state will be removed automatically at a later date.
         */
        DELETED,
        /**
         * This is similar to deleted, however, Glance has not yet removed
         * the image data. An image in this state is not recoverable.
         */
        PENDING_DELETE;

        @JsonCreator
        public static Status value(String v)
        {
            if (v == null) return UNRECOGNIZED;
            try {
                return valueOf(v.toUpperCase());
            } catch (IllegalArgumentException e) {
                return UNRECOGNIZED;
            }
        }

        @JsonValue
        public String value() {
            return name().toLowerCase();
        }
    }


    /**
     * @return image status
     */
    Status getStatus();

    /**
     * @return image name.
     */
    String getName();

    /**
     * @return a list of tag objects
     */
    //TODO: should this be tag objects? they seem to me like just strings
    // but they could change in the future...?
    List<String> getTags();

    /**
     * @return the container format of the image
     */
    ContainerFormat getContainerFormat();

    /**
     * @return the ISO 8601 date and time when the resource was created
     */
    // TODO: correct format? this should be iso 8601
    Date getCreatedAt();

    /**
     * @return the disk format of the image
     */
    DiskFormat getDiskFormat();

    /**
     * @return the ISO 8601 date and time when the resource was updated
     */
    Date getUpdatedAt();

    /**
     * @return the minimum disk size in GB that is required to boot the image
     */
    Integer getMinDisk();

    /**
     * @return image protection for deletion
     * Default is false
     */
    Boolean getIsProtected();

    /**
     * @return UUID of the image
     */
    String getId();

    /**
     * @return the minimum amount of RAM in MB that is required to boot the image
     */
    Integer getMinRam();

    /**
     * @return hash that is used over the image data
     * (image service uses this value for verification)
     */
    String getChecksum();

    /**
     * @return the id of teh owner, or tenant, of the image
     */
    String getOwner();

    /**
     * @return image visibility (public or private)
     * Default is private
     */
    //TODO: switch to enum? valid values are public and private
    String getVisibility();

    /**
     * @return the size of the image data, in bytes
     */
    Integer getSize();

    /**
     * @return A list of URLs to access the image file in external store.
     *
     * This list appears if the show_multiple_locations option is
     * set to true in the Image service's configuration file.
     */
    List<String> getLocations();

    /**
     * @return the location metadata
     */
    // todo: how to show metadata? json doesn't show
    String getLocationMetadata();

    /**
     * @return the image properties, if any
     */
    String getProperties();

    /**
     * @return the URL to access the image file kept in external store
     * This value appears when you set {@code show_image_direct_url} option to
     * {@code true} in the image service's configuration file
     */
    String getDirectUrl();

    /**
     * @return the URL for the virtual machine image
     */
    String getSelf();

    /**
     * @return the URL for the virtual machine image file
     */
    String getFile();

    /**
     * @return the URL for the schema of teh virtual machine image
     */
    String getSchema();
}