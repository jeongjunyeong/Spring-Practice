SELECT
    id, username, password, role
FROM jwt2_user ORDER BY id DESC;

update jwt2_user set username = upper(username);

