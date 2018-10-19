$(document).ready(function(){
	$("#ignore").click(function(){
	    swal ( "Error",  "Jabatan Diduduki oleh Pegawai",  "error");
	});
	$("#success").click(function(){
	    swal ( "Berhasil",  "Jabatan Berhasil Dihapus",  "success");
	});
});