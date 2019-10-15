window.onload = function () {
    var imageUpload = document.getElementById("uploadimage");
    imageUpload.value= "";
    var div = document.createElement("div");
    div.className="column"
    imageUpload.onchange = function () {
        var view  = document.getElementById("view");
        for(var i=0;i< imageUpload.files.length; i++){
            var image = imageUpload.files[i];
            var reader = new FileReader();
            reader.readAsDataURL(image);
            reader.onload = function(e){
                var img = document.createElement("img");
                img.src = e.target.result;
                div.appendChild(img);
                view.appendChild(div);
            }
        }
    }
};
