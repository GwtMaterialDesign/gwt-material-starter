package gwt.material.starter.client.application;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import gwt.material.design.client.base.SearchObject;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.events.SearchFinishEvent;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialSearch;
import gwt.material.design.client.ui.MaterialToast;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ApplicationView extends ViewImpl implements ApplicationPresenter.MyView {

    interface Binder extends UiBinder<Widget, ApplicationView> {
    }

    @UiField
    MaterialNavBar navBar, navBarSearch;

    @UiField
    MaterialSearch txtSearch;

    @Inject
    ApplicationView(
            Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
        txtSearch.addCloseHandler(new CloseHandler<String>() {
            @Override
            public void onClose(CloseEvent<String> event) {
                navBar.setVisible(true);
                navBarSearch.setVisible(false);
            }
        });
        txtSearch.addSearchFinishHandler(new SearchFinishEvent.SearchFinishHandler() {
            @Override
            public void onSearchFinish(SearchFinishEvent event) {
                MaterialToast.fireToast("You search : " + txtSearch.getSelectedObject().getKeyword());
            }
        });
    }

    @UiHandler("btnSearch")
    void onSearch(ClickEvent e) {
        navBar.setVisible(false);
        navBarSearch.setVisible(true);
    }

    @Override
    public void onGenerateListSeach() {
        List<SearchObject> searches = new ArrayList<>();
        searches.add(new SearchObject(IconType.INFO, "Apple"));
        searches.add(new SearchObject(IconType.INFO, "Orange"));
        searches.add(new SearchObject(IconType.INFO, "Banana"));
        searches.add(new SearchObject(IconType.INFO, "Billiard"));
        searches.add(new SearchObject(IconType.INFO, "Ball"));
        searches.add(new SearchObject(IconType.INFO, "Cat"));
        txtSearch.setListSearches(searches);
    }

}
