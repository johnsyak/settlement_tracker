package com.settlement_tracker.TrackerInterfaces;

import com.settlement_tracker.sqllite.dao.AbilityDAO;
import com.settlement_tracker.sqllite.dao.AbilityDetailDAO;
import com.settlement_tracker.sqllite.dao.AttributesDAO;
import com.settlement_tracker.sqllite.dao.CourageDAO;
import com.settlement_tracker.sqllite.dao.HealthDAO;
import com.settlement_tracker.sqllite.dao.InjuriesDAO;
import com.settlement_tracker.sqllite.dao.PlayerDAO;
import com.settlement_tracker.sqllite.dao.SettlementDAO;
import com.settlement_tracker.sqllite.dao.SurvivalDAO;
import com.settlement_tracker.sqllite.dao.TraitsDAO;
import com.settlement_tracker.sqllite.dao.UnderstandingDAO;

/**
 * Created by John on 9/7/2017.
 */

public interface SQLiteSchema {
    String CREATE_TABLE = " CREATE TABLE ";
    String PK_INCREMENT = " INTEGER PRIMARY KEY AUTOINCREMENT, ";
    String FK = "FOREIGN KEY (";

    String SQL_CREATE_TABLE_SETTLEMENT = CREATE_TABLE + SettlementDAO.TABLE + " ( "
            + SettlementDAO.COLUMN_ID + PK_INCREMENT
            + SettlementDAO.COLUMN_NAME + " TEXT NOT NULL, "
            + SettlementDAO.COLUMN_NOTES + " TEXT "
            + " );";

    String SQL_CREATE_TABLE_PLAYER = CREATE_TABLE + PlayerDAO.TABLE + " ( "
            + PlayerDAO.COLUMN_ID + PK_INCREMENT
            + PlayerDAO.COLUMN_SETTLEMENT_ID + " INTEGER, "
            + PlayerDAO.COLUMN_NAME + " TEXT, "
            + PlayerDAO.COLUMN_GENDER + " INTEGER, "
            + PlayerDAO.COLUMN_NOTES + " TEXT, "
            + FK + PlayerDAO.COLUMN_SETTLEMENT_ID + ") REFERENCES settlement(settlement_ID) ON DELETE CASCADE "
            + " ); ";

    String SQL_CREATE_TABLE_ATTRIBUTES = CREATE_TABLE + AttributesDAO.TABLE + "("
            + AttributesDAO.COLUMN_ID + PK_INCREMENT
            + AttributesDAO.COLUMN_PLAYER_ID + " INTEGER, "
            + AttributesDAO.COLUMN_SURVIVAL + " INTEGER, "
            + AttributesDAO.COLUMN_MOVEMENT + " INTEGER, "
            + AttributesDAO.COLUMN_ACCURACY + " INTEGER, "
            + AttributesDAO.COLUMN_STRENGTH + " INTEGER, "
            + AttributesDAO.COLUMN_EVASION + " INTEGER, "
            + AttributesDAO.COLUMN_LUCK + " INTEGER, "
            + AttributesDAO.COLUMN_SPEED + " INTEGER, "
            + FK + AttributesDAO.COLUMN_PLAYER_ID + ") REFERENCES player(player_ID) on DELETE CASCADE"
            + " ); ";

    String SQL_CREATE_TABLE_SURVIVAL = CREATE_TABLE + SurvivalDAO.TABLE + "("
            + SurvivalDAO.COLUMN_ID + PK_INCREMENT
            + SurvivalDAO.COLUMN_ATTRIBUTES_ID + " INTEGER, "
            + SurvivalDAO.COLUMN_SURVIVAL_STAT + " INTEGER, "
            + SurvivalDAO.COLUMN_SURVIVAL + " INTEGER, "
            + SurvivalDAO.COLUMN_DODGE + " INTEGER, "
            + SurvivalDAO.COLUMN_ENCOURAGE + " INTEGER, "
            + SurvivalDAO.COLUMN_SURGE + " INTEGER, "
            + SurvivalDAO.COLUMN_DASH + " INTEGER, "
            + FK + SurvivalDAO.COLUMN_ATTRIBUTES_ID + ") REFERENCES attributes(attributes_ID) on DELETE CASCADE "
            + " ); ";

    String SQL_CREATE_TABLE_HEALTH = CREATE_TABLE + HealthDAO.TABLE + "("
            + HealthDAO.COLUMN_ID + PK_INCREMENT
            + HealthDAO.COLUMN_ATTRIBUTES_ID + " INTEGER, "
            + HealthDAO.COLUMN_INSANITY + " INTEGER, "
            + HealthDAO.COLUMN_HEAD + " INTEGER, "
            + HealthDAO.COLUMN_ARMS + " INTEGER, "
            + HealthDAO.COLUMN_BODY + " INTEGER, "
            + HealthDAO.COLUMN_WAIST + " INTEGER, "
            + HealthDAO.COLUMN_LEGS + " INTEGER, "
            + HealthDAO.COLUMN_BASE_HEAD + " INTEGER, "
            + HealthDAO.COLUMN_BASE_ARMS + " INTEGER, "
            + HealthDAO.COLUMN_BASE_BODY + " INTEGER, "
            + HealthDAO.COLUMN_BASE_WAIST + " INTEGER, "
            + HealthDAO.COLUMN_BASE_LEGS + " INTEGER, "
            + FK + HealthDAO.COLUMN_ATTRIBUTES_ID + ") REFERENCES attributes(attributes_ID) on DELETE CASCADE "
            + " ); ";

    String SQL_CREATE_TABLE_INJURIES = CREATE_TABLE + InjuriesDAO.TABLE + "("
            + InjuriesDAO.COLUMN_ID + PK_INCREMENT
            + InjuriesDAO.COLUMN_HEALTH_ID + " INTEGER, "
            + InjuriesDAO.COLUMN_INSANITY + " INTEGER, "
            + InjuriesDAO.COLUMN_HEAD + " INTEGER, "
            + InjuriesDAO.COLUMN_ARMS + " INTEGER, "
            + InjuriesDAO.COLUMN_BODY + " INTEGER, "
            + InjuriesDAO.COLUMN_WAIST + " INTEGER, "
            + InjuriesDAO.COLUMN_LEGS + " INTEGER, "
            + FK + InjuriesDAO.COLUMN_HEALTH_ID + ") REFERENCES health(health_ID) on DELETE CASCADE "
            + " ); ";

    String SQL_CREATE_TABLE_TRAITS = CREATE_TABLE + TraitsDAO.TABLE + "("
            + TraitsDAO.COLUMN_ID + PK_INCREMENT
            + TraitsDAO.COLUMN_PLAYER_ID + " INTEGER, "
            + TraitsDAO.COLUMN_HUNT_XP + " INTEGER, "
            + TraitsDAO.COLUMN_PROFICIENCY_TYPE + " TEXT, "
            + TraitsDAO.COLUMN_WEAPON_PROFICIENCY + " INTEGER, "
            + TraitsDAO.COLUMN_IS_SPECIALIST + " INTEGER, "
            + TraitsDAO.COLUMN_IS_MASTER + " INTEGER, "
            + FK + TraitsDAO.COLUMN_PLAYER_ID + ") REFERENCES player(player_ID) on DELETE CASCADE "
            + " ); ";

    String SQL_CREATE_TABLE_ABILITIES = CREATE_TABLE + AbilityDAO.TABLE + "("
            + AbilityDAO.COLUMN_ID + PK_INCREMENT
            + AbilityDAO.COLUMN_TRAITS_ID + " INTEGER, "
            + AbilityDAO.COLUMN_FIGHTING_ARTS1 + " TEXT, "
            + AbilityDAO.COLUMN_FIGHTING_ARTS2 + " TEXT, "
            + AbilityDAO.COLUMN_FIGHTING_ARTS3 + " TEXT, "
            + AbilityDAO.COLUMN_DISORDERS1 + " TEXT, "
            + AbilityDAO.COLUMN_DISORDERS2 + " TEXT, "
            + AbilityDAO.COLUMN_DISORDERS3 + " TEXT, "
            + AbilityDAO.COLUMN_IMPAIRMENTS1 + " TEXT, "
            + AbilityDAO.COLUMN_IMPAIRMENTS2 + " TEXT, "
            + AbilityDAO.COLUMN_IMPAIRMENTS3 + " TEXT, "
            + FK + AbilityDAO.COLUMN_TRAITS_ID + ") REFERENCES traits(traits_ID) on DELETE CASCADE"
            + " ); ";

    String SQL_CREATE_TABLE_ABILITY_DETAILS = CREATE_TABLE + AbilityDetailDAO.TABLE + " ("
            + AbilityDetailDAO.COLUMN_ID + PK_INCREMENT
            + AbilityDetailDAO.COLUMN_ABILITIES_ID + " INTEGER, "
            + AbilityDetailDAO.COLUMN_CAN_USE_ARTS + " INTEGER, "
            + AbilityDetailDAO.COLUMN_SKIP_HUNT + " INTEGER, "
            + AbilityDetailDAO.COLUMN_MAX_ARTS + " INTEGER, "
            + AbilityDetailDAO.COLUMN_MAX_DISORDERS + " INTEGER, "
            + FK + AbilityDetailDAO.COLUMN_ABILITIES_ID + ") REFERENCES ability(ability_ID) on DELETE CASCADE"
            + " ); ";

    String SQL_CREATE_TABLE_UNDERSTANDING = CREATE_TABLE + UnderstandingDAO.TABLE + "("
            + UnderstandingDAO.COLUMN_ID + PK_INCREMENT
            + UnderstandingDAO.COLUMN_TRAITS_ID + " INTEGER, "
            + UnderstandingDAO.COLUMN_UNDERSTANDING_STAT + " INTEGER, "
            + UnderstandingDAO.COLUMN_INSIGHT + " INTEGER, "
            + UnderstandingDAO.COLUMN_WHITE_SECRET + " INTEGER, "
            + UnderstandingDAO.COLUMN_ANALYZE + " INTEGER, "
            + UnderstandingDAO.COLUMN_EXPLORE + " INTEGER, "
            + UnderstandingDAO.COLUMN_TINKER + " INTEGER, "
            + FK + UnderstandingDAO.COLUMN_TRAITS_ID + ") REFERENCES traits(traits_ID) on DELETE CASCADE"
            + " ); ";

    String SQL_CREATE_TABLE_COURAGE = CREATE_TABLE + CourageDAO.TABLE + " ("
            + CourageDAO.COLUMN_ID + PK_INCREMENT
            + CourageDAO.COLUMN_PROFICIENCY_ID + " INTEGER, "
            + CourageDAO.COLUMN_COURAGE + " INTEGER, "
            + CourageDAO.COLUMN_BOLD + " INTEGER, "
            + CourageDAO.COLUMN_TRUTH + " INTEGER, "
            + CourageDAO.COLUMN_STALWART + " INTEGER, "
            + CourageDAO.COLUMN_PREPARED + " INTEGER, "
            + CourageDAO.COLUMN_MATCHMAKER + " INTEGER, "
            + FK + CourageDAO.COLUMN_PROFICIENCY_ID + ") REFERENCES traits(traits_ID) on DELETE CASCADE"
            + " ); ";
}
