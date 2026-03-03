package ru.prplhd.tennisscoreboard.util;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public final class PaginationHelper {
    public static final int FIRST_PAGE = 1;
    public static final int DEFAULT_PAGE_LIMIT_SIZE = 4;
    public static final int WINDOW_SIZE = 5;
    public static final int PAGES_AROUND_CURRENT = WINDOW_SIZE / 2;

    public static int getTotalPages(int matchesCount) {
        int totalPages = (matchesCount + DEFAULT_PAGE_LIMIT_SIZE - 1) / DEFAULT_PAGE_LIMIT_SIZE;

        if (totalPages == 0) {
            return FIRST_PAGE;
        }

        return totalPages;
    }

    public static int getPage(String pageParameterValue, int totalPages) {
        int page;

        if (pageParameterValue == null || pageParameterValue.isBlank()) {
            return FIRST_PAGE;

        } else {
            try {
                page = Integer.parseInt(pageParameterValue.trim());
                if (page < FIRST_PAGE) {
                    page = FIRST_PAGE;
                }
            } catch (NumberFormatException ignored) {
                return FIRST_PAGE;
            }
        }

        if (page > totalPages) {
            return totalPages;
        }

        return page;
    }

    public static int getOffset(int page) {
        return (page - 1) * DEFAULT_PAGE_LIMIT_SIZE;
    }

    public static Map<String, Integer> getPagesWindow(int currentPage, int totalPages) {
        int start = currentPage - PAGES_AROUND_CURRENT;
        start = Math.max(1, start);

        int end = currentPage + PAGES_AROUND_CURRENT;
        end = Math.min(end, totalPages);

        if ( (end - start + 1) < WINDOW_SIZE ) {
            if (start == 1) {
                end = Math.min(totalPages, start + WINDOW_SIZE - 1);
            } else if (end == totalPages) {
                start = Math.max(1, end - WINDOW_SIZE + 1);
            }
        }

        return Map.of("start", start, "end", end);
    }

}
