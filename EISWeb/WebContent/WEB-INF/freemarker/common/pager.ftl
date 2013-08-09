<#-- 分页（Pager对象、链接URL、参数Map、最大页码显示数） -->
<#macro pager pager baseUrl parameterMap = {} maxShowPageCount = 6>

	<#local pageNumber = pager.pageNumber />
	<#local pageSize = pager.pageSize />
	<#local pageCount = pager.pageCount />
	<#local property = pager.property />
	<#local keyword = pager.keyword />
	<#local orderBy = pager.orderBy />
	<#local orderType = pager.orderType />
	
	<#local parameter = "" />
	<#if (pageSize != "")!>
		<#local parameter = parameter + "&pager.pageSize=" + pageSize />
	</#if>
	<#if (property != "")!>
		<#local parameter = parameter + "&pager.property=" + property />
	</#if>
	<#if (keyword != "")!>
		<#local parameter = parameter + "&pager.keyword=" + keyword />
	</#if>
	<#if (orderBy != "")!>
		<#local parameter = parameter + "&pager.orderBy=" + orderBy />
	</#if>
	<#if (orderType != "")!>
		<#local parameter = parameter + "&pager.orderType=" + orderType />
	</#if>
	<#list parameterMap?keys as key>
		<#if parameterMap[key] != null && parameterMap[key] != "">
			<#local parameter = parameter + "&" + key + "=" + parameterMap[key] />
		</#if>
	</#list>
	
	<#if baseUrl?contains("?")>
		<#local baseUrl = baseUrl + "&" />
	<#else>
		<#local baseUrl = baseUrl + "?" />
	</#if>
	<#local firstPageUrl = baseUrl + "pager.pageNumber=1" + parameter />
	<#local lastPageUrl = baseUrl + "pager.pageNumber=" + pageCount + parameter />
	<#local prePageUrl = baseUrl + "pager.pageNumber=" + (pageNumber - 1) + parameter />
	<#local nextPageUrl = baseUrl + "pager.pageNumber=" + (pageNumber + 1) + parameter />

	<#if maxShowPageCount <= 0>
		<#local maxShowPageCount = 6>
	</#if>
	
	<#local segment = ((pageNumber - 1) / maxShowPageCount)?int + 1 />
	<#local startPageNumber = (segment - 1) * maxShowPageCount + 1 />
	<#local endPageNumber = segment * maxShowPageCount />
	<#if (startPageNumber < 1)>
		<#local startPageNumber = 1 />
	</#if>
	<#if (endPageNumber > pageCount)>
		<#local endPageNumber = pageCount />
	</#if>

	<#if (pageCount > 1)>
	<div class="pager_area">
		<ul class="pager">
			<li class="pageInfo">
				共 ${pageCount} 页
			</li>
			<#-- 首页 -->
			<#if (pageNumber > 1)>
				<li class="firstPage">
					<a href="${base}${firstPageUrl}">首页</a>
				</li>
			<#else>
				<li class="firstPage">
					<span>首页</span>
				</li>
			</#if>
			
			<#-- 上一页 -->
			<#if (pageNumber > 1)>
				<li class="prePage">
					<a href="${base}${prePageUrl}">上一页</a>
				</li>
			<#else>
				<li class="prePage">
					<span>上一页</span>
				</li>
			</#if>
			
			<#if (startPageNumber > 1)>
				<li>
					<a href="${base}${baseUrl + "pager.pageNumber=" + (pageNumber - 2) + parameter}">...</a>
				</li>
			</#if>
			
			<#list startPageNumber .. endPageNumber as index>
				<#if pageNumber != index>
					<li>
						<a href="${base}${baseUrl + "pager.pageNumber=" + index + parameter}">${index}</a>
					</li>
				<#else>
					<li class="currentPage">
						<span>${index}</span>
					</li>
				</#if>
			</#list>
			
			<#if (endPageNumber < pageCount)>
				<li>
					<a href="${base}${baseUrl + "pager.pageNumber=" + (pageNumber + 2) + parameter}">...</a>
				</li>
			</#if>
		    
			<#-- 下一页 -->
			<#if (pageNumber < pageCount)>
				<li class="nextPage">
					<a href="${base}${nextPageUrl}">下一页</a>
				</li>
			<#else>
				<li class="nextPage">
					<span>下一页</span>
				</li>
			</#if>
			
			<#-- 末页 -->
			<#if (pageNumber < pageCount)>
				<li class="lastPage">
					<a href="${base}${lastPageUrl}">末页</a>
				</li>
			<#else>
				<li class="lastPage">
					<span>末页</span>
				</li>
			</#if>
		</ul>
		</div>
	</#if>

</#macro>

<#macro appendPreviewParameter url isPreview>
	<#if isPreview?? && isPreview>
		<#if url?index_of("?") &lt; 0>
			${url}?preview=true
		<#else>
			${url}&preview=true
		</#if>
	<#else>
		${url}
	</#if>
</#macro>

<#-- 文章内容分页 -->
<#macro articleContentPager article pageCount baseUrl pageNumber isPreview = false maxShowPageCount = 5>
	<#if baseUrl?index_of("?") &lt; 0>
		<#local param = "?page=" />
	<#else>
		<#local param = "&page=" />
	</#if>
	
	<#local firstPageUrl = baseUrl />
	<#local lastPageUrl = baseUrl + param + pageCount />
	<#if pageNumber == 1>
		<#local prePageUrl = firstPageUrl />
	<#else>
		<#local prePageUrl = baseUrl + param + (pageNumber - 1) />
	</#if>
	<#if pageNumber == pageCount>
		<#local nextPageUrl = lastPageUrl />
	<#else>
		<#local nextPageUrl = baseUrl + param + (pageNumber + 1) />
	</#if>

	<#if maxShowPageCount <= 0>
		<#local maxShowPageCount = 6>
	</#if>
	
	<#local segment = ((pageNumber - 1) / maxShowPageCount)?int + 1 />
	<#local startPageNumber = (segment - 1) * maxShowPageCount + 1 />
	<#local endPageNumber = segment * maxShowPageCount />
	<#if (startPageNumber < 1)>
		<#local startPageNumber = 1 />
	</#if>
	<#if (endPageNumber > pageCount)>
		<#local endPageNumber = pageCount />
	</#if>

	<#if (pageCount > 1)>
		<ul class="pager">
			<li class="pageInfo">
				共 ${pageCount} 页
			</li>
			<#-- 首页 -->
			<#if (pageNumber > 1)>
				<li class="firstPage">
					<a href="<#call appendPreviewParameter base + firstPageUrl isPreview>">首页</a>
				</li>
			<#else>
				<li class="firstPage">
					<span>首页</span>
				</li>
			</#if>
			
			<#-- 上一页 -->
			<#if (pageNumber > 1)>
				<li class="prePage">
					<a href="<#call appendPreviewParameter base + prePageUrl isPreview>">上一页</a>
				</li>
			<#else>
				<li class="prePage">
					<span>上一页</span>
				</li>
			</#if>
			
			<#if (startPageNumber > 1)>
				<li>
					<a href="<#call appendPreviewParameter base + baseUrl + param + (startPageNumber - 1) isPreview>">...</a>
				</li>
			</#if>
			
			<#list startPageNumber .. endPageNumber as index>
				<#if pageNumber != index>
					<li>
						<#if index == 1>
							<a href="<#call appendPreviewParameter base + baseUrl isPreview>">${index}</a>
						<#else>
							<a href="<#call appendPreviewParameter base + baseUrl + param + index isPreview>">${index}</a>
						</#if>
					</li>
				<#else>
					<li class="currentPage">
						<span>${index}</span>
					</li>
				</#if>
			</#list>
			
			<#if (endPageNumber < pageCount)>
				<li>
					<a href="<#call appendPreviewParameter base + baseUrl + param + (endPageNumber + 1) isPreview>">...</a>
				</li>
			</#if>
		    
			<#-- 下一页 -->
			<#if (pageNumber < pageCount)>
				<li class="nextPage">
					<a href="<#call appendPreviewParameter base + nextPageUrl isPreview>">下一页</a>
				</li>
			<#else>
				<li class="nextPage">
					<span>下一页</span>
				</li>
			</#if>
			
			<#-- 末页 -->
			<#if (pageNumber < pageCount)>
				<li class="lastPage">
					<a href="<#call appendPreviewParameter base + lastPageUrl isPreview>">末页</a>
				</li>
			<#else>
				<li class="lastPage">
					<span>末页</span>
				</li>
			</#if>
		</ul>
	</#if>
</#macro>