import org.dubna.main.utils.FileTwinsUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SearchAndCompareDuplicateTests {
    private final static String root1 = "C:\\tmp";
    private final static String root2 = "C:\\tmp";
    private static FileTwinsUtils fileTwinsUtils;

    @BeforeAll
    public static void before(){
        fileTwinsUtils = new FileTwinsUtils(root1, root2);
    }

    @Test
    public void testSearchAllFiles(){
        List<FileTwinsUtils.CompareFile> fileList1 = fileTwinsUtils.getFileList1();
        List<FileTwinsUtils.CompareFile> fileList2 = fileTwinsUtils.getFileList2();
        Assertions.assertTrue(fileList1.size() > 0);
        Assertions.assertEquals(fileList1.size(), fileList2.size());
        Assertions.assertEquals(220, fileList1.size());
    }

    @Test
    public void testCompare(){
        Map<FileTwinsUtils.CompareFile, List<FileTwinsUtils.CompareFile>> resultMap = fileTwinsUtils.compareFiles();
        Assertions.assertEquals(5, resultMap.size());
        Assertions.assertEquals(9, resultMap.values().stream().mapToInt(Collection::size).sum());
    }
}

