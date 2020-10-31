<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Account activation</title>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

        <link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>

        <!-- use the font -->
        <style>
            body {
                font-family: 'Roboto', sans-serif;
            }
        </style>
    </head>
    <body style="margin: 0; padding: 0;">
        <h1>Bonjour ${ userFirstName }</h1>
        <div>
            <p>Suivre le lien suivant pour activer votre compte</p>
            <a href="${ activateAccountUrl }">Activer mon compte</a>
        </div>
    </body>
</html>
