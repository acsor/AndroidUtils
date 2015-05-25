package org.n0ne.utils.database;

public class QueryFilter {

    private String selection;
    private String[] selectionArgs;
    private String orderBy;
    private String limit;

    public QueryFilter () {
    }

    public QueryFilter (String selection, String[] selectionArgs, String orderBy, String limit) {
        this.selection = selection;
        this.selectionArgs = selectionArgs;
        this.orderBy = orderBy;
        this.limit = limit;
    }

    public String getSelection () {
        return selection;
    }

    public String[] getSelectionArgs () {
        return selectionArgs;
    }

    public String getOrderBy () {
        return orderBy;
    }

    public String getLimit () {
        return limit;
    }

    public void setSelectionArgs (String[] selectionArgs) {
        this.selectionArgs = selectionArgs;
    }

}
