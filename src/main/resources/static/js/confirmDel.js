$(document).on("click", "a.confirm", function(e) {
    e.preventDefault();
    if (confirm('Kliknij ok, by potwierdzic operacje usunięcia produktu')) {
        location.href = $(this).attr('href');
    }
});