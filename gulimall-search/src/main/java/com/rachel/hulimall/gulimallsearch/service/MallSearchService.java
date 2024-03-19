package com.rachel.hulimall.gulimallsearch.service;

import com.rachel.hulimall.gulimallsearch.vo.SearchParam;
import com.rachel.hulimall.gulimallsearch.vo.SearchResult;

public interface MallSearchService {

    SearchResult search(SearchParam searchParam);

}
