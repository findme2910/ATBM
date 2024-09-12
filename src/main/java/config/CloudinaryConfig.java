package config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.util.Map;

public class CloudinaryConfig {
    private static Cloudinary cloudinary;

    static {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dvxnipyk4",
                "api_key", "692147617982258",
                "api_secret", "hfXoMc2I3MMYYDPEBQ1Iw_8kJwI"
        ));
    }

    public static Cloudinary getCloudinary() {
        return cloudinary;
    }

    public static void main(String[] args) {
        try {
            Cloudinary cloudinary = CloudinaryConfig.getCloudinary();
            File file = new File("C:\\Users\\Admin1\\Pictures\\Nitro\\1223350.jpg");
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("secure_url");
            System.out.println("Image URL: " + imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
