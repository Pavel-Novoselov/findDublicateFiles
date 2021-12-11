import org.dubna.main.utils.FileTwinsUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SearchAndCompareDuplicateTests {
    private static final String root1 = "C:\\tmp\\1";
    private static final String root2 = "C:\\tmp\\1";
    private static FileTwinsUtils fileTwinsUtils;

    @BeforeAll
    public static void before(){
        fileTwinsUtils = new FileTwinsUtils(root1, root2);
    }

    @Test
    public void testSearchAllFiles(){
        final List<FileTwinsUtils.CompareFile> fileList1 = fileTwinsUtils.getFileList1();
        final List<FileTwinsUtils.CompareFile> fileList2 = fileTwinsUtils.getFileList2();
        Assertions.assertFalse(fileList1.isEmpty());
        Assertions.assertEquals(fileList1.size(), fileList2.size());
        Assertions.assertEquals(16, fileList1.size());
    }

    @Test
    public void testCompare(){
        final Map<FileTwinsUtils.CompareFile, List<FileTwinsUtils.CompareFile>> resultMap = fileTwinsUtils.compareFiles();
        Assertions.assertEquals(5, resultMap.size());
        Assertions.assertEquals(12, resultMap.values().stream().mapToInt(Collection::size).sum() + resultMap.keySet().size());
    }
}

