function deleteById(path, id) {
    $.ajax({
        url: `/${path}/${id}`,
        type: 'DELETE',
        success: function() {
            location.reload();
        }
    });
}