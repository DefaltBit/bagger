import re
from transfer import utils, log
from transfer.database import AbstractDB

class PackageModeler(AbstractDB):
    def __init__(self, config):
        AbstractDB.__init__(self, config, project_name="packagemodeler-ndnp")
        self.original_db_name = "package_modeler"
        self.db_name = self.db_prefix + self.original_db_name
        self.roles = {
            'transfer_role': 'package_modeler_role',
            'transfer_user': 'transfer_user',
        }
        self.passwds = {
            'transfer_user': config['TRANSFER_PASSWD'] if config['TRANSFER_PASSWD'] else "transfer_user",
        }
        self.datasources_props = "%s/%s-%s/conf/datasources.properties" % (
            self.install_dir, self.project_name, self.version
        )
        self.process_def = "files/processdefinition.xml"
        self.logger = log.Log(self.project_name)

    def create_database(self):
        """ creates database """
        return None

    def create_roles(self):
        """ populates database roles """
        return None

    def drop(self):
        """ drops database """
        return None

    def deploy_process_def(self, driver):
        """ deploys process definition """
        result = utils.deploy_process_def(driver, self.process_def)
        for line in result.splitlines():
            if self.debug:
                self.logger.debug(line)
            m = re.compile(r'ERROR (.+)').search(line)
            if m:
                self.logger.error("Error deploying process definition: %s" % (m.groups(1)))
                raise RuntimeError("Error deploying process definition: %s" % (m.groups(1)))
            if line.find("Deployment succeeded") != -1:
                self.logger.info("Deploying process definition")
                return
    