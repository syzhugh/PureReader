#开发说明书
* 注解使用 ButterKnife 7.0.1 
* 
<<<<<<< .merge_file_a10556
        compile 'com.jakewharton:butterknife:7.0.1'

* api文档
* 
		channelName 	新闻频道名称，可模糊匹配。用频道关键字即可，如：军事，财经，				国内，国际等等。
		原App中可以实现的栏目
		头条 汽车 房产 科技 星座 旅游 时尚 娱乐（划线的不能实现）
		可以添加的栏目
		国内 国际 互联网 体育 游戏 	
		needHtml		是否需要返回正文的html格式，1为需要，其他为不需要
		Page			页数，默认1。每页最多20条记录
		maxResult		每页返回记录数，值在0-100之间
		needAllList		是否需要最全的返回资料（比较复杂，可以先不用）。包括每一段文				本和每一张图。用list的形式返回。默认是1，改成0
=======
        compile 'com.jakewharton:butterknife:7.0.1'
>>>>>>> .merge_file_a05704
