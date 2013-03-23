function Tree(rootNode) {
	var $ = this;
	this.root = rootNode;
	
	this.show = function(container) {
		$.update($.root);
		this.root.expand();
		
		if(container.tagName)
			container.appendChild($.root.container);
		else if(typeof container == "string")
			document.getElementById(container).appendChild($.root.container);
	}
	
	this.update = function(parent) {
		parent.indent();
		for (var i = 0; i < parent.children.length; i++) {
			parent.children[i].level = parent.level + 1;
			for (var j = 0; j < parent.ancestor.length; j++) {
				parent.children[i].ancestor.push(parent.ancestor[j]);
			}
			parent.children[i].ancestor.push(parent);
			$.update(parent.children[i]);
		}
	}
}