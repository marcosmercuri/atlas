CREATE TABLE proposed_exercises (
  type varchar(31) NOT NULL,
  id varchar(255) NOT NULL,
  description varchar(255) DEFAULT NULL,
  female_rx_in_kilograms double DEFAULT NULL,
  male_rx_in_kilograms double DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  duration_in_seconds int(11) DEFAULT NULL,
  distance_in_meters double DEFAULT NULL,
  repetitions int(11) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE proposed_workouts (
  type varchar(31) NOT NULL,
  id varchar(255) NOT NULL,
  duration_in_seconds int(11) DEFAULT NULL,
  max_allowed_seconds int(11) DEFAULT NULL,
  number_of_rounds int(11) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE proposed_workouts_exercises (
  proposed_workouts_id varchar(255) NOT NULL,
  exercises_id varchar(255) NOT NULL,
  UNIQUE KEY UK_exercise_id (exercises_id),
  CONSTRAINT FK_proposed_workouts_exercises_exercises_id FOREIGN KEY (exercises_id) REFERENCES proposed_exercises (id),
  CONSTRAINT FK_proposed_workouts_exercises_proposed_workouts_id FOREIGN KEY (proposed_workouts_id) REFERENCES proposed_workouts (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE result_exercise (
  type varchar(31) NOT NULL,
  id varchar(255) NOT NULL,
  comments varchar(255) DEFAULT NULL,
  proposed_exercise_id varchar(255) NOT NULL,
  completed_rounds int(11) DEFAULT NULL,
  repetitions_on_unfinished_round int(11) DEFAULT NULL,
  replace_exercise varchar(255) DEFAULT NULL,
  weight_in_kilograms float DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE result_workout (
  id varchar(255) NOT NULL,
  comments varchar(255) DEFAULT NULL,
  date DATE,
  finish_time_in_seconds int(11) DEFAULT NULL,
  finished bit(1) DEFAULT NULL,
  rx bit(1) DEFAULT NULL,
  user_id varchar(255) DEFAULT NULL,
  proposed_workout_id varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_result_workouts_proposed_workouts FOREIGN KEY (proposed_workout_id) REFERENCES proposed_workouts (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE result_workout_result_exercises (
  result_workout_id varchar(255) NOT NULL,
  result_exercises_id varchar(255) NOT NULL,
  UNIQUE KEY UK_result_workout_result_exercises (result_exercises_id),
  CONSTRAINT FK_result_workout_result_exercises_result_exercises_id FOREIGN KEY (result_exercises_id) REFERENCES result_exercise (id),
  CONSTRAINT FK_result_workout_result_exercises_result_workout_id FOREIGN KEY (result_workout_id) REFERENCES result_workout (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;
