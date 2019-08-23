jQuery(document).ready(function ($) {

	/**
	 * Login form AJAX handler
	 */
	$('#login-form').submit(function (event) {
		event.preventDefault();
		
		var form = $(this);
		var invalidData = form.find(".alertify-log-error");
		var data = {
			email: $('input[name="email"]').val(),
			password: $('input[name="password"]').val(),
			_csrf: $('input[name="_csrf"]').val()
		};

		$.ajax({
			data: data,
			timeout: 1000,
			type: 'POST',
			url: '/login',
			beforeSend: function() {
				form.jmspinner();
			},
			complete: function(){
				form.jmspinner(false);
			}
		}).done((responseText, statusText, response) => {
			invalidData.hide();
			window.location = "/?logged";
		}).fail((response, statusText) => {
			sleep(1500); //TODO REMOVE IN PROD
			invalidData.show();
		});
	});

	/**
	 * Delay for a number of milliseconds
	 */
	function sleep(delay) {
		var start = new Date().getTime();
		while (new Date().getTime() < start + delay);
	}

});