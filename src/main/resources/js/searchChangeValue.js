  function changeValue(vale){
	document.getElementById("category").value = vale;
	document.getElementById("cat").innerHTML = vale;
	
	document.getElementById("cat_ul").style.display='none';
	
	
}
function changeValue1(vale){
	if(vale=="All Ireland")
	{
		document.getElementById("hdcity").value ="";  
		document.getElementById("area").innerHTML = vale;
	
		document.getElementById("county_ul").style.display='none';
		
	}
	else
	{
		document.getElementById("hdcity").value = vale;  
		document.getElementById("area").innerHTML = vale;
	
		document.getElementById("county_ul").style.display='none';
	}
}

function mouseCat(){
	
	document.getElementById("cat_ul").style.display='block';
}

function mouseCounty(){
	document.getElementById("county_ul").style.display='block';
}


function login_pop_up(showhide){
	if(showhide == "show"){
		document.getElementById('popupbox').style.visibility="visible"; /* If the function is called with the variable 'show', show the login box */
	}
	if(showhide == "hide"){
		document.getElementById('popupbox').style.visibility="hidden"; /* If the function is called with the variable 'show', show the login box */
	}
}

function formReset(){
	
	
document.getElementById('frm1').reset();
}

function getCat(catg,area){
	alert(catg);
	document.getElementById("category").value = catg;
	//alert(document.getElementById("category").value);
}

function show(elem){
	//alert(elem)
}

function flip(){
}

function open_item(auth,cat_val,main){
    window.location = "item_desc.php?auth=" + auth + "&typ=" +  cat_val + "&main=" + main ;
    //alert(auth.toString())
}


