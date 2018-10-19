$(document).ready( function () {
    $('#pegawai_table').DataTable();
    
    $("#kriteria-form").submit(function (e) {
    	if ($('#form-value1').val() == '' && $('#form-value2').val() == '' && $('#form-value3').val() == '') {
            e.preventDefault();
    		swal ( "Tidak dapat mencari pegawai!",  "Anda belum memilih kriteria",  "error");
        }
    });
} );