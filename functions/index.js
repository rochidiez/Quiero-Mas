const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);


// https://us-central1-quiero-mas.cloudfunctions.net/registrar?text=firebaseID/nombre/nacimiento/mail/fechaBebe/nombreBebe/apodoBebe
exports.registrar = functions.https.onRequest((req, res) => {
	const original = req.query.text;
	console.log('original: ', original)
	var params = original.split(' ')
	console.log('params: ', params)
	if (params.length >= 7) {
		var firebaseID = params[0]
		var nombre = params[1]
		var nacimiento = params[2]
		var mail = params[3]
		var fechaBebe = params[4]
	 	var nombreBebe = params[5]
		var apodoBebe = params[6]
		
		var nombreSpace = nombre.replace("-", " ");
		var nombreBebeSpace = nombreBebe.replace("-", " ");
		var apodoBebeSpace = apodoBebe.replace("-", " ");
		console.log('registrando')
		console.log('firebaseID: ', firebaseID)
		console.log('nombreSpace: ', nombreSpace)
		console.log('nacimiento: ', nacimiento)
		console.log('mail: ', mail)
		console.log('fechaBebe: ', fechaBebe)
		console.log('nombreBebe: ', nombreBebeSpace)
		console.log('apodoBebe', apodoBebeSpace)

		//Datos
		if (nombreSpace != '') {
			admin.database().ref('/Usuarios').child(firebaseID).child('Datos').child('Nombre Completo').set(nombreSpace);
		}
		
		if (mail != '') {
			admin.database().ref('/Usuarios').child(firebaseID).child('Datos').child('Email').set(mail);
		}
		
		if (nacimiento != '') {
			admin.database().ref('/Usuarios').child(firebaseID).child('Datos').child('Fecha de Nacimiento').set(nacimiento);
		}
		

		//Bebe
		if (nombreBebeSpace != '' && apodoBebeSpace != '') {
			admin.database().ref('/Usuarios').child(firebaseID).child('Bebé').child('Apodo').set(apodoBebeSpace);
			admin.database().ref('/Usuarios').child(firebaseID).child('Bebé').child('Nombre').set(nombreBebeSpace);
			admin.database().ref('/Usuarios').child(firebaseID).child('Bebé').child('Fecha de Nacimiento').set(fechaBebe);

			return admin.database().ref('Bebés de menos de 6 meses').once('value').then(snap => {
		    if (snap.exists()) {
		    	if (snap.child(firebaseID).exists()) {
		    		var refString = '/Bebés de menos de 6 meses/' + firebaseID;
		    		var adaRef = admin.database().ref(refString);
					adaRef.remove()
					  .then(function() {
					    console.log("Remove succeeded.")
					  })
					  .catch(function(error) {
					    console.log("Remove failed: " + error.message)
					  });
		    	}
		    }

		  });
		} else {
			if (fechaBebe != '') {
				admin.database().ref('/Bebés de menos de 6 meses').child(firebaseID).set(fechaBebe);
			}
		}
	}
});
