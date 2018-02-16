-- setup callables for each resource type
-- when a job is retrieved with the given entity type, that callable will be created
INSERT INTO EntityType (id, className) VALUES ('1977615988', 'com.walterjwhite.datastore.api.model.entity.Tag');

INSERT INTO Tag (id, name, description) VALUES ('1', 'red', 'the color red');
INSERT INTO Tag (id, name, description) VALUES ('2', 'orange', 'the color orange');
INSERT INTO Tag (id, name, description) VALUES ('3', 'yellow', 'the color yellow');
INSERT INTO Tag (id, name, description) VALUES ('4', 'green', 'the color green');
INSERT INTO Tag (id, name, description) VALUES ('5', 'blue', 'the color blue');

INSERT INTO Tag (id, name, description) VALUES ('6', 'purple', 'the color purple');
INSERT INTO Tag (id, name, description) VALUES ('7', 'black', 'the color black');
INSERT INTO Tag (id, name, description) VALUES ('8', 'brown', 'the color brown');
INSERT INTO Tag (id, name, description) VALUES ('9', 'pink', 'the color pink');
INSERT INTO Tag (id, name, description) VALUES ('10', 'magenta', 'the color magenta');

-- 4 users
-- items 10 - 18
INSERT INTO EntityReference (id, entityTypeId, entityId) VALUES ('1', '1977615988', '1');
INSERT INTO EntityReference (id, entityTypeId, entityId) VALUES ('2', '1977615988', '2');
INSERT INTO EntityReference (id, entityTypeId, entityId) VALUES ('3', '1977615988', '3');
INSERT INTO EntityReference (id, entityTypeId, entityId) VALUES ('4', '1977615988', '4');
INSERT INTO EntityReference (id, entityTypeId, entityId) VALUES ('5', '1977615988', '5');

INSERT INTO EntityReference (id, entityTypeId, entityId) VALUES ('6', '1977615988', '6');
INSERT INTO EntityReference (id, entityTypeId, entityId) VALUES ('7', '1977615988', '7');
INSERT INTO EntityReference (id, entityTypeId, entityId) VALUES ('8', '1977615988', '8');
INSERT INTO EntityReference (id, entityTypeId, entityId) VALUES ('9', '1977615988', '9');
INSERT INTO EntityReference (id, entityTypeId, entityId) VALUES ('10', '1977615988', '10');

COMMIT;
