package icycream;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ModelGenerator {
    public static final String SIMPLE_ITEM_MODEL = "{\n" +
            "    \"parent\": \"item/generated\",\n" +
            "    \"textures\": {\n" +
            "        \"layer0\": \"%s:item/%s\"\n" +
            "    }\n" +
            "}";
    public static final String SIMPLE_BLOCK_MODEL = "{\n" +
            "    \"parent\": \"block/cube\",\n" +
            "    \"textures\": {\n" +
            "        \"particle\": \"%s/%s\",\n" +
            "        \"all\": \"%s/%s\"\n" +
            "    }" +
            "}";

    public static void generateSimpleItemJson(String modId, String registryName) throws IOException {
        String prefix = new File(ModelGenerator.class.getResource("/pack.mcmeta").getFile()).getParent() + "/../../../src/main/resources/assets/" + modId + "/models/item/";
        //new File(prefix).mkdirs();
        File file = new File(prefix + registryName + ".json");
        FileUtils.write(file, String.format(SIMPLE_ITEM_MODEL, modId, registryName));
    }

    public static void generateSimpleBlockJson(String modId, String registryName) throws IOException {
        String prefix = new File(ModelGenerator.class.getResource("/pack.mcmeta").getFile()).getParent() + "/../../../src/main/resources/assets/" + modId + "/models/item/";
        //new File(prefix).mkdirs();
        File file = new File(prefix + registryName + ".json");
        FileUtils.write(file, String.format(SIMPLE_ITEM_MODEL, modId, registryName, modId, registryName));
    }

    public static void main(String[] args) throws IOException {
        System.out.println(ModelGenerator.class.getResource("/pack.mcmeta").getFile());
        generateSimpleItemJson("icycream", "ice_cream_complex");
    }
}
