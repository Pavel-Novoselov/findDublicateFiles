package org.dubna.main;

import org.dubna.main.ui.TwinJFrame;

public class Main {
    public static void main(String[] args) {
//        final Logger logger = Logger.getLogger("logger");
//        String root1 = "C:\\tmp";
//        String root2 = "C:\\tmp";
//        FindTwins findTwins = new FindTwins(root1, root2);
//        Map<FindTwins.CompareFile, List<FindTwins.CompareFile>> result = findTwins.compareFiles();
//        logger.info("found " + result.size() + "matches");
//        if (result.isEmpty()){
//            //one or both fordelrs is empty
//            logger.warning("one or both fordelrs is empty");
//        }
//        System.out.println("Result:");
//        AtomicInteger i = new AtomicInteger();
//        result.forEach((compareFile1, compareFile2) -> {
//            System.out.println((i.getAndIncrement() + 1) + " match:");
//            System.out.println(compareFile1);
//            System.out.println(compareFile2 + "\n");
//        });
//        System.out.println("found " + i + " match");
//
        TwinJFrame twinJFrame = new TwinJFrame();
        twinJFrame.setVisible(true);
    }
}
