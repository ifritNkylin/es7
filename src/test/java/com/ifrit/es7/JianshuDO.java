package com.ifrit.es7;

@lombok.NoArgsConstructor
@lombok.Data
public class JianshuDO {


    /**
     * id : 24373896
     * title : [selenium]selenium驱动<em class='search-result-highlight'>chrome</em>爬取网页/<em class='search-result-highlight'>无</em><em class='search-result-highlight'>界面</em><em class='search-result-highlight'>chrome</em>/使用代理
     * slug : 4d677a1b506b
     * content : ……selenium与chromedriver安装 安装<em class='search-result-highlight'>chrome</em>（有版本要求，linux和windows版本要求不同，可自行查阅，尽量使用61+版本的<em class='search-result-highlight'>chrome</em>） 先安装selenium库，在下载chromedriver……selenium调用<em class='search-result-highlight'>chrome</em> 代码可以直接运行，只需修改代理参数，该代码实现了： <em class='search-result-highlight'>无</em>代理爬取京东单个商品：selenium+headless <em class='search-result-highlight'>chrome</em> 普通代理爬取京东单个商品：selenium……+headless <em class='search-result-highlight'>chrome</em>+proxy 需要验证的代理爬取京东单个商品：selenium+headless <em class='search-result-highlight'>chrome</em>+proxy(auth)  (暂时<em class='search-result-highlight'>无</em>法使用headless方式) 有问题请留言咨询……
     * user : {"id":5718317,"nickname":"蛮三刀把刀","slug":"b5f225ca2376","avatar_url":"https://upload.jianshu.io/users/upload_avatars/5718317/f30221ef-e592-4e44-a04d-a7fcf5bd9431.jpg"}
     * notebook : {"id":11890486,"name":"日记本"}
     * commentable : true
     * public_comments_count : 0
     * likes_count : 1
     * views_count : 1394
     * total_rewards_count : 0
     * first_shared_at : 2018-02-25T09:37:14.000Z
     */

    private int id;
    private String title;
    private String slug;
    private String content;
    private UserBean user;
    private NotebookBean notebook;
    private boolean commentable;
    private int public_comments_count;
    private int likes_count;
    private int views_count;
    private int total_rewards_count;
    private String first_shared_at;

    @lombok.NoArgsConstructor
    @lombok.Data
    public static class UserBean {
        /**
         * id : 5718317
         * nickname : 蛮三刀把刀
         * slug : b5f225ca2376
         * avatar_url : https://upload.jianshu.io/users/upload_avatars/5718317/f30221ef-e592-4e44-a04d-a7fcf5bd9431.jpg
         */

        private int id;
        private String nickname;
        private String slug;
        private String avatar_url;
    }

    @lombok.NoArgsConstructor
    @lombok.Data
    public static class NotebookBean {
        /**
         * id : 11890486
         * name : 日记本
         */

        private int id;
        private String name;
    }
}

