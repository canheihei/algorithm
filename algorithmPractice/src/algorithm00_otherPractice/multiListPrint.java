package algorithm00_otherPractice;

import algorithm00_common.Category;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class multiListPrint {
    public static void main(String[] args) {
        List<Category> categories = Arrays.asList(
                new Category(1, 0, "电子产品"),
                new Category(2, 1, "手机"),
                new Category(3, 1, "电脑"),
                new Category(4, 2, "智能手机"),
                new Category(5, 2, "功能手机"),
                new Category(6, 0, "服装")
        );

        printWithStream(categories);
    }

    public static void printWithStream(List<Category> categories) {
        // 构建分类树（流式）
        List<Category> tree = categories.stream()
                .filter(c -> c.getParentId() == 0)  // 先过滤出根节点
                .peek(c -> c.setChildren(getChildrenStream(c, categories)))  // 设置子节点
                .sorted(Comparator.comparing(Category::getName))  // 按名称排序
                .collect(Collectors.toList());

        // 打印树（流式递归）
        printTreeStream(tree, 0);
    }

    /** 流式获取子分类 */
    private static List<Category> getChildrenStream(Category parent, List<Category> allCategories) {
        return allCategories.stream()
                .filter(c -> c.getParentId() == parent.getId())  // 匹配父ID
                .peek(child -> child.setChildren(getChildrenStream(child, allCategories)))  // 递归设置子节点
                .sorted(Comparator.comparing(Category::getName))  // 子节点排序
                .collect(Collectors.toList());
    }

    /** 流式打印树结构 */
    private static void printTreeStream(List<Category> categories, int level) {
        categories.forEach(cat -> {
            System.out.println(repeat("    ",level) + cat.getName());
            if (!cat.getChildren().isEmpty()) {
                printTreeStream(cat.getChildren(), level + 1);  // 递归打印
            }
        });
    }

    public static String repeat(String str, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count 不能为负数: " + count);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
}
