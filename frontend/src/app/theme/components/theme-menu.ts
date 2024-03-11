import { NbMenuItem } from '@nebular/theme';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
let perms = AuthenticationService.getPrivilegeTest();
if(perms == null){
  perms = ['ROLE_FOCAL_STRUCT','ROLE_USER'];
}
export const MENU_ITEMS: NbMenuItem[] = [
  {
    title: 'Accueil',
    icon: 'home-outline',
    link: '/workspace',
    home: true,
  },
  {
    title: 'Tableau de bord SEPAP',
    icon: 'pie-chart-outline',
    home: false,
    hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_DIRCAB_MIN','ROLE_SG_MIN','ROLE_DIR_DGESS','ROLE_RESP_MIN','ROLE_RESP_DGESS']),
    children: [
      {
        title: 'Suivi des activités',
        icon: 'briefcase-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_DIRCAB_MIN','ROLE_SG_MIN','ROLE_DIR_DGESS','ROLE_RESP_MIN','ROLE_RESP_DGESS']),
        children: [
          {
            title: 'Suivi structurel',
            icon: 'checkmark-square-outline',
            link: '/workspace/dashboard/dhsstructure',
            hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_DIRCAB_MIN','ROLE_SG_MIN','ROLE_DIR_DGESS','ROLE_RESP_MIN','ROLE_RESP_DGESS']),
          },
          {
            title: 'Suivi sectoriel',
            icon: 'checkmark-circle-outline',
            link: '/workspace/dashboard/dhssectoriel',
            hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_DIRCAB_MIN','ROLE_SG_MIN','ROLE_DIR_DGESS','ROLE_RESP_MIN','ROLE_RESP_DGESS']),
          },
          {
            title: 'Finances',
            icon: 'done-all-outline',
            link: '/workspace/dashboard/dhsfinance',
            hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_DIRCAB_MIN','ROLE_SG_MIN','ROLE_DIR_DGESS','ROLE_RESP_MIN','ROLE_RESP_DGESS']),
          },
          {
            title: 'Evolutions',
            icon: 'clipboard-outline',
            link: '/workspace/dashboard/dhsevolution',
            hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_DIRCAB_MIN','ROLE_SG_MIN','ROLE_DIR_DGESS','ROLE_RESP_MIN','ROLE_RESP_DGESS']),
          },
          {
            title: 'Rapports',
            icon: 'camera-outline',
            link: '/workspace/dashboard/dhsrapport',
            hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_DIRCAB_MIN','ROLE_SG_MIN','ROLE_DIR_DGESS','ROLE_RESP_MIN','ROLE_RESP_DGESS']),
          },
        ]
      },
      //Performance
      {
        title: 'Performances',
        icon: 'briefcase-outline',
       // link: '/',
       hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_DIRCAB_MIN','ROLE_SG_MIN','ROLE_DIR_DGESS','ROLE_RESP_MIN','ROLE_RESP_DGESS']),
        children: [
          {
            title: 'Par structure',
            icon: 'checkmark-square-outline',
            link: '/workspace/dashboard/perfstructure',
          },
          {
            title: 'Sectorielle',
            icon: 'clipboard-outline',
            link: '/workspace/dashboard/perfsectorielle',
          },
          {
            title: 'Evolutions',
            icon: 'trending-up-outline',
            link: '/workspace/dashboard/perfevolution',
          },
          {
            title: 'Rapports',
            icon: 'camera-outline',
            link: '/workspace/dashboard/perfrapport',
          },
        ]
      }
    ]
  },
  {
    title: 'Programmation Activités',
    icon: 'grid-outline',
    hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_DIR_DGESS','ROLE_RESP_DGESS','ROLE_FOCAL_STRUCT','ROLE_RESP_STRUCT']),
    children: [
      {
        title: 'La liste des activités',
        icon: 'list-outline',
        link: '/workspace/activite',
      },
      {
        title: 'Programmations',
        icon: 'list-outline',
        link: '/workspace/liste-programmation',
      },
      {
        title: 'Evolutions',
        icon: 'list-outline',
        link: '/workspace/liste-evaluations',
      },
      {
        title: 'Evaluations',
        icon: 'list-outline',
        link: '/workspace/programme-activites',
      }
    ]
  },

  {
    title: 'Performance structures',
    icon: 'grid-outline',
    hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_STRUCT','ROLE_FOCAL_STRUCT','ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_RESP_DDII']),
    children: [

      {
        title: 'Etats performances',
        link: '/workspace/performances/performers',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_STRUCT','ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_RESP_DDII'])
      },
      {
        title: 'Indic Gouvenance',
        link: '/workspace/performances/evaluation-gouvernance',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_STRUCT','ROLE_FOCAL_STRUCT','ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_RESP_DDII'])
      },
      {
        title: 'Mes Indic Gouv',
        link: '/workspace/performances/mes-criteres-gouvernance',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_STRUCT','ROLE_FOCAL_STRUCT','ROLE_RESP_DGESS','ROLE_DIR_DGESS'])
      },
      {
        title: 'Impact',
        link: '/workspace/performances/impact',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_STRUCT','ROLE_FOCAL_STRUCT','ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_RESP_DDII'])
      },
      {
        title: 'Parametrer',
        link: '/workspace/performances/parametre-impact',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_STRUCT','ROLE_FOCAL_STRUCT','ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_RESP_DDII'])

      },
      {
        title: 'Contribuer',
        link: '/workspace/performances/contribuer',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_STRUCT','ROLE_FOCAL_STRUCT','ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_RESP_DDII'])
      },
    /*  {
        title: 'Etats performance',
        link: '/workspace/performances/liste-performance',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_STRUCT','ROLE_RESP_DGESS','ROLE_DIR_DGESS'])
      },*/
    ],
  },
  {
    title: 'Notifications',
    link: '/workspace/notifications',
    icon: 'email-outline',
    hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_RESP_DDII']),
  },

  {
    title: 'Mes Parametres',
    icon: 'settings-outline',
    hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_DAF','ROLE_RESP_DDII']),
    children: [

      {
        title: 'Structures',
        link: '/workspace/structure',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_RESP_DDII']),

      },
      {
        title: 'Ministeres',
        link: '/workspace/ministere',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_RESP_DDII'])

      },
      {
        title: 'Types activités',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_DGESS','ROLE_DIR_DGESS']),
        link: '/workspace/type-activite',
      },
      {
        title: 'Exercices',
        link: '/workspace/exercice',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_DGESS','ROLE_DIR_DGESS']),
      },
      {
        title: 'Financements',
        link: '/workspace/sources-financement',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_DAF']),
      },

      {
        title: 'Programmes',
        link: '/workspace/programme',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_DGESS','ROLE_DIR_DGESS']),

      },

      {
        title: 'Projets',
        link: '/workspace/projet',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_DGESS','ROLE_DIR_DGESS']),
      },
      {
        title: 'Objectifs',
        link: '/workspace/objectif',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_DGESS','ROLE_DIR_DGESS']),
      },
      {
        title: 'Grille de Perf',
        link: '/workspace/grille-performance',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_DGESS','ROLE_DIR_DGESS']),
      },
      {
        title: 'Indicateurs',
        link: '/workspace/indicateur',
        // link: '/workspace/indicateurImpact',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_RESP_DDII']),

      },
      {
        title: 'Actions',
        link: '/workspace/action',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_DGESS','ROLE_DIR_DGESS']),

      },

      {
        title: 'Periodes',
        link: '/workspace/periode',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_DGESS','ROLE_DIR_DGESS']),

      },
      {
        title: 'Periodicités',
        link: '/workspace/periodicite',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_DGESS','ROLE_DIR_DGESS']),

      },
      {
        title: 'Paramètre',
        link: '/workspace/parametre',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_DGESS','ROLE_DIR_DGESS']),

      },
      {
        title: 'Ponderations',
        link: '/workspace/ponderation',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_DGESS','ROLE_DIR_DGESS']),

      },
      {
        title: 'Indic Gouvernance',
        link: '/workspace/criteres',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_RESP_DDII']),

      }
    ],
  },
  {
    title: 'Utilisateurs',
    icon: 'lock-outline',
    hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_STRUCT','ROLE_RESP_DGESS','ROLE_DIR_DGESS']),
    children: [

      {
        title: 'Compte',
        link: '/workspace/compte',
        icon: 'person-add-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN','ROLE_RESP_STRUCT','ROLE_RESP_DGESS','ROLE_DIR_DGESS'])
      },
      {
        title: 'Agents',
        link: '/workspace/agent',
        icon: 'people-outline'

      },
      {
        title: 'Profils',
        link: '/workspace/profil',
        icon: 'list-outline',
        hidden:!AuthenticationService.checkPermissionTest(perms!,['ROLE_ADMIN'])
      },
    ],
  }
];
