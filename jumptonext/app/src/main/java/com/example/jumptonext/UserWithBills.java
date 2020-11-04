package com.example.jumptonext;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithBills {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "name",
            entityColumn = "UserName"
    )
    public List<Bill> bills;
}
