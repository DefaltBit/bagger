<form method="POST" action="j_security_check">

  Username: <input type="text"     name="j_username" /><br />
  Password: <input type="password" name="j_password" /><br />
  <br />

  <input type="submit" value="Login" />
  <input type="reset"  value="Reset" />
</form>

<table border="1">
	<thead>
		<tr>
			<th>Username</th>
			<th>Password</th>
			<th>Roles</th>
		</tr>
	</thead>
	<tr>
		<td>ray</td>
		<td>ray</td>
		<td>ndnp-participant</td>
	</tr>
	<tr>
		<td>myron</td>
		<td>myron</td>
		<td>ndnp-participant</td>
	</tr>
	<tr>
		<td>scott</td>
		<td>scott</td>
		<td>ndnp-participant<br/>ndnp-administrator<br/>wdl-participant<br/>wdl-administrator</td>
	</tr>
    <tr>
		<td>brian</td>
		<td>brian</td>
		<td>ndnp-participant</td>    
    </tr>
	<tr>
		<td>dan</td>
		<td>dan</td>
		<td>wdl-participant</td>
	</tr>
</table>