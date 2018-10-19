$(document).ready(function(){
	$('#provinsi').change(function() {
		valueProv = $(this).val();
		$.ajax({
			type: "GET",
			url: "http://localhost:8080/pegawai/tambah/instansi?provinsi=",
			data: {
				'provinsiId' : valueProv
			},
			dataType: 'json',
			success: function(data) {
				console.log(data);
				getIns = '';
				var size = data.length;
			
				for (var i = 0; i<size; i++) {
					getIns+='<option value="'+data[i].id+'">'+data[i].nama+'</option>';
					console.log(data[i]);
					console.log(data[i].nama)
				}
				console.log(getIns);
				$('#instansi').append(getIns);
			}
		})
	})
});
