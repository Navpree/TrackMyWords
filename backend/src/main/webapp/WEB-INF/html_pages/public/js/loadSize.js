window.onload = function(){
    resize();
    window.addEventListener('resize', resize);
    window.addEventListener('scroll', resize);
};

var resize = function(){
    var cover = document.getElementById('load-cover');
    var image = document.getElementById('cover-image');

    cover.style.width = window.innerWidth + 'px';
    cover.style.height = window.innerHeight + 'px';

    image.style.left = (window.innerWidth / 2 - 50) + 'px';
    image.style.top = (window.innerHeight / 2 - 50) + 'px';
    
    var doc = document.documentElement;
    var left = (window.pageXOffset || doc.scrollLeft) - (doc.clientLeft || 0);
    var top = (window.pageYOffset || doc.scrollTop)  - (doc.clientTop || 0);
    
    cover.style.top = top + 'px';
};