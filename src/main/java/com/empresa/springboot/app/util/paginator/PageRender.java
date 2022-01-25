package com.empresa.springboot.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {
	
	private String url;
	private Page<T> page;
	
	private int totalPages;
	private int numElemPerPage;
	private int currentPage;
	private List<PageItem> pages;
	
	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		this.pages = new ArrayList<PageItem>();
		
		numElemPerPage = page.getSize();
		totalPages = page.getTotalPages();
		currentPage = page.getNumber() + 1;
		
		int from, to;
		if (totalPages <= numElemPerPage) {
			from = 1;
			to = totalPages;
		} else {
			if (currentPage <= numElemPerPage/2) {
				from = 1;
				to = numElemPerPage;
			} else if (currentPage >= totalPages - numElemPerPage/2) {
				from = totalPages - numElemPerPage + 1;
				to = numElemPerPage;				
			} else {
				from = currentPage - numElemPerPage/2;
				to = numElemPerPage;
			}
		}
		
		for(int i=0; i < to; i++) {
			pages.add(new PageItem(from + i, currentPage == from+i));
		}
		
	}

	public String getUrl() {
		return url;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public List<PageItem> getPages() {
		return pages;
	}
	
	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	public boolean isHasNext() {
		return page.hasNext();
	}
	
	public boolean isHasPrevious() {
		return page.hasPrevious();
	}
	
	
	

}
