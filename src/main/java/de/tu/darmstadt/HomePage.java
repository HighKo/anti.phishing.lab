package de.tu.darmstadt;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	
	private Label selectedLabel;

    public HomePage(final PageParameters parameters) {
		super(parameters);
				
		String testURL = "http://host.second-level.fl/path/document.type?query=magic";
		
		String[] components = splitURLStringIntoComponents(testURL);
		if (components == null){
			return;
		}

		final RepeatingView urlLabelComponents = new RepeatingView("urlBasedOutOfRepeatingLabels");
		add(urlLabelComponents);
		
		final List<Label> allLabels = new ArrayList<Label>();
		
		for (String aComponent : components){	
			final Label aLabel = new Label(urlLabelComponents.newChildId(), aComponent);			
			aLabel.setOutputMarkupId(true);
			allLabels.add(aLabel);
			urlLabelComponents.add(aLabel);
		}
		
		for (final Label aLabel : allLabels){
			aLabel.add(new AjaxEventBehavior("click"){
				private static final long serialVersionUID = 1L;

				@Override
				protected void onEvent(AjaxRequestTarget target) {
					unmarkAllLabels(allLabels, target);
					
					selectedLabel = aLabel;
					
					aLabel.add(markedLabelAttributeModifier());
					target.add(aLabel);
				}

				private void unmarkAllLabels(final List<Label> allLabels, AjaxRequestTarget target) {
					for (Label labelToResetMarkedStyle : allLabels){
						labelToResetMarkedStyle.add(unmarkedLabelAttributeModifier());
						target.add(labelToResetMarkedStyle);
					}
				}

				private AttributeModifier markedLabelAttributeModifier() {
					String styleAttr = "background-color: lightblue;";
					return new AttributeModifier("style", styleAttr);
				}

				private AttributeModifier unmarkedLabelAttributeModifier() {
					String style = "background-color: white;";
					return new AttributeModifier("style", style);
				}			
			});
		}

    }

	private String[] splitURLStringIntoComponents(String testURL) {
		
		//maybe use guava instead?
		
		URI u;
		try {
			u = new URI(testURL);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}

		String scheme = u.getScheme();
		scheme = scheme+"://";
		String userInfo = u.getUserInfo(); 
		String host = u.getHost(); 
		String port = "";
		if (u.getPort() >= 0){
			port = Integer.toString(u.getPort());
		}
		String path = u.getPath(); 
		String query = u.getQuery();
		if (query.length() > 0) {
			query = "?" + query;
		}
		String fragment = u.getFragment();

		String[] components = {scheme, userInfo, host, port, path, query, fragment};
		return components;
	}
}