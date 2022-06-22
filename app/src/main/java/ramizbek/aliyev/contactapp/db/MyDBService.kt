package ramizbek.aliyev.contactapp.db

import ramizbek.aliyev.contactapp.models.User

interface MyDBService {

    fun createUser(user: User)

    fun readUser():List<User>

    fun updateUser(user: User):Int

    fun deleteUser(user: User)

}