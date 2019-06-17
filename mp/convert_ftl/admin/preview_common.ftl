<style>
    .show_eg{
        display: inline-block;
        position: relative;
        padding-right: 5px;
        color: #5A8BFF;
        text-decoration: none;
    }
    .show_eg:hover{
        color: #5a8bff;
        text-decoration: none;
    }
    .hover_show{
        position: absolute;
        left: 68px;
        top: -45px;
        padding: 20px;
        background-color: #fff;
        border-radius: 5px;
        display: none;
        z-index: 3;
        box-shadow:1px 1px 10px 5px #eee;
    }
    .hover_show:before{
        content: ' ';
        position: absolute;
        top: 48px;
        left: -7px;
        width: 14px;
        height: 14px;
        background-color: #fff;
        transform: rotate(-45deg);
        z-index: 4;
        box-shadow: -3px -3px 3px #e5e5e5;
    }
    .hover_show img{
        width: 200px;
        height: 355.74px;
        border: 1px solid #eee;
    }
    .TempContainer {
        position: fixed;
        z-index: 101;
        top: 0px;
        left: 0px;
        text-align: center;
        width: 100%;
        height: 100%;
        cursor: pointer;
        background: rgba(0,0,0,0.7);
        display: none;
    }
    .TempContainer img{
       height: 100%;
    }

</style>
<div class="TempContainer">
    <img src="" alt="">
</div>

<script>
    $('.show_eg').hover(function(){
        $(this).find('.hover_show').show()
    },function(){
        $(this).find('.hover_show').hide()
    })

    $('.hover_show').click(function () {
        var img = $(this).find('img').attr('src');
        $('.TempContainer img').attr('src',img);
        $('.TempContainer').show();
    })
    $('.TempContainer').click(function(){
        $(this).hide();
    })


</script>