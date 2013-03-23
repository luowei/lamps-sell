var currentNode = null;

function TreeNode(text, url, iconOpen, iconOpenHover, iconClosed, iconClosedHover) {
	var $ = this;
	this.level = 0;	
	this.children = [];	
	this.parent = null;	
	this.status = "CLOSED";	
	this.ancestor = [];	
	this.isHover = false;
	
	this.PATH = "../images/tree/";
	this.COLLAPSED = this.PATH + "arrow_collapsed.gif";
	this.EXPANDED = this.PATH + "arrow_expanded.gif";
	this.COLLAPSED_HOVER = this.PATH + "arrow_collapsed_hover.gif";
	this.EXPANDED_HOVER = this.PATH + "arrow_expanded_hover.gif";
	this.CATEGORYOPEN = this.PATH + (iconOpen ? iconOpen : "folder_open.gif");
	this.CATEGORYOPEN_HOVER = this.CATEGORYOPEN;
	this.CATEGORYCLOSED = this.PATH + (iconClosed ? iconClosed : "folder_closed.gif");
	this.CATEGORYCLOSED_HOVER = this.CATEGORYCLOSED;
	this.EMPTY = this.PATH + "empty.gif";

	this.container = document.createElement("DIV");
	this.content = document.createElement("DIV");
	this.indentSpace = document.createElement("SPAN");
	this.statusIcon = document.createElement("IMG");
	this.node = document.createElement("SPAN");
	this.nodeIcon = document.createElement("IMG");
	this.label = document.createElement("A");
	this.container.appendChild(this.content);
	this.content.appendChild(this.indentSpace);
	this.content.appendChild(this.statusIcon);
	this.content.appendChild(this.node);
	this.node.appendChild(this.nodeIcon);
	this.node.appendChild(this.label);

	this.container.style.display = "block";
	this.statusIcon.src = this.COLLAPSED;
	this.nodeIcon.src = this.CATEGORYCLOSED;	
	this.nodeIcon.align = "absmiddle";
	this.statusIcon.align = "absmiddle";
	this.statusIcon.style.cursor = "default";
	this.node.style.cursor = "default";
	this.label.style.lineHeight = "20px";
	this.label.style.fontSize = "12px";
	this.label.style.display = "inline-block";
	this.label.style.backgroundImage = "url(" + this.BG + ")";
	this.label.style.backgroundRepeat = "repeat-x";
	this.label.innerHTML = text;
	
	if (url) {
	    this.label.href = url;
	    this.label.target = "mainFrame";
	}

	this.add = function(child) {
		this.container.appendChild(child.container);
		this.children.push(child);
		child.parent = this;
	}
	
	this.remove = function(child) {
		child.container.removeNode(true);
		var temp = [];
		for (var i = 0; i < this.children.length; i++) {
			if (this.children[i] != child) {
				temp.push(this.children[i]);
			} else {
				continue;
			}
		}
		this.children = temp;
	}

	this.hidden = function() {
		this.container.style.display = "none";
	}

	this.show = function() {
		this.container.style.display = "block";
	}

	this.getAncestor = function(level) {
		if (this.level == level)
			return this;
		for (var i = 0; i < $.ancestor.length; i++) {
			if ($.ancestor[i].level == level) {
				return $.ancestor[i];
			}
		}
		return null;
	}
	
	this.contains = function(node) {
		for (var i = 0; i < $.children.length; i++) {
			if ($.children[i] == node) {
				return true;
			}
			$.children[i].contains(node);
		}
		return false;
	}
	
	this.indent = function() {
		this.indentSpace.innerHTML = "";
		for (var i = 0; i < this.level; i++) {
			var indentImg = document.createElement("IMG");
			indentImg.src = this.EMPTY;
			indentImg.align = "absmiddle";
			this.indentSpace.appendChild(indentImg);
		}
		this.collapse();
	}
	
	this.setIcon = function() {
		this.nodeIcon = this.status == "CLOSED" ? 
		($.isHover ? $.CATEGORYCLOSED_HOVER : $.CATEGORYCLOSED) : 
		($.isHover ? $.CATEGORYOPEN_HOVER : $.CATEGORYOPEN);
	}
	
	this.collapse = function() {
		for (var i = 0; $.children && i < $.children.length; i++) {
			$.children[i].hidden();
		}
		$.statusIcon.src = $.COLLAPSED;
		$.nodeIcon.src = $.CATEGORYCLOSED;
		$.status = "CLOSED";
	}
	
	this.expand = function() {
		for (var i = 0; $.children && i < $.children.length; i++) {
			$.children[i].show();
		}
		$.statusIcon.src = $.EXPANDED;			
		$.nodeIcon.src = $.CATEGORYOPEN;
		$.status = "OPEN";
	}
	
	this.expandOrCollapse = function() {
		if ($.status == "CLOSED") {
			if (currentNode) {
				var ancestor = currentNode.getAncestor(1);
				var myAncestor = $.getAncestor(1);
				if (ancestor && myAncestor && ancestor != myAncestor) {
					ancestor.collapse();
				}
			}
			currentNode = $;
			$.expand();
		} else {
			$.collapse();
		}
	}

	this.node.onmousedown = function() {
		if (currentNode) {
			currentNode.nodeIcon.src = (currentNode.status == "CLOSED" ? currentNode.CATEGORYCLOSED : currentNode.CATEGORYOPEN);
		}
	}
	
	this.node.onmouseup = function() {
		if (event.button == 2) {
			
		}
	}
	
	this.content.onselectstart = function() {
		return false;	
	}

	this.statusIcon.onclick = this.expandOrCollapse;
	this.nodeIcon.ondblclick = this.expandOrCollapse;
	this.label.onclick = this.expandOrCollapse;
	
	this.statusIcon.onmouseover = function() {
		this.src = $.status == "CLOSED" ? $.COLLAPSED_HOVER : $.EXPANDED_HOVER;
	}
	
	this.statusIcon.onmouseout = function() {
		this.src = $.status == "CLOSED" ? $.COLLAPSED : $.EXPANDED;
	}
}