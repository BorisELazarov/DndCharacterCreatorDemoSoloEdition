import { Routes } from "@angular/router";
import { ClassCreateComponent } from "./features/classes/class-create/class-create.component";
import { ClassDeletedListComponent } from "./features/classes/class-deleted-list/class-deleted-list.component";
import { ClassDetailsComponent } from "./features/classes/class-details/class-details.component";
import { ClassEditComponent } from "./features/classes/class-edit/class-edit.component";
import { ClassListComponent } from "./features/classes/class-list/class-list.component";
import { CreateProficiencyComponent } from "./features/proficiencies/create-proficiency/create-proficiency.component";
import { EditProficiencyComponent } from "./features/proficiencies/edit-proficiency/edit-proficiency.component";
import { ProficiencyDeletedListComponent } from "./features/proficiencies/proficiency-deleted-list/proficiency-deleted-list.component";
import { ProficiencyDetailsComponent } from "./features/proficiencies/proficiency-details/proficiency-details.component";
import { ProficiencyListComponent } from "./features/proficiencies/proficiency-list/proficiency-list.component";
import { SpellListComponent } from "./features/spells/spell-list/spell-list.component";
import { SpellDetailsComponent } from "./features/spells/spell-details/spell-details.component";
import { SpellCreateComponent } from "./features/spells/spell-create/spell-create.component";
import { SpellEditComponent } from "./features/spells/spell-edit/spell-edit.component";
import { SpellDeletedListComponent } from "./features/spells/spell-deleted-list/spell-deleted-list.component";
import { CharacterListComponent } from "./features/characters/character-list/character-list.component";
import { CharacterCreationComponent } from "./features/characters/character-creation/character-creation.component";
import { CharacterSheetComponent } from "./features/characters/character-sheet/character-sheet.component";
import { CharacterDeletedListComponent } from "./features/characters/character-deleted-list/character-deleted-list.component";
import { ProfileDetailsComponent } from "./core/profile-management/profile-details/profile-details.component";
import { ChangePasswordComponent } from "./core/profile-management/change-password/change-password.component";
import { ChangeUsernameComponent } from "./core/profile-management/change-username/change-username.component";
import { ChangeEmailComponent } from "./core/profile-management/change-email/change-email.component";
import { UserListComponent } from "./features/user-management/user-list/user-list.component";
import { ChangeRoleComponent } from "./features/user-management/change-role/change-role.component";
import { PageNotFoundComponent } from "./core/page-not-found/page-not-found.component";
import { HomeComponent } from "./core/home/home.component";
import { LoginComponent } from "./core/auth/login/login.component";
import { RegisterComponent } from "./core/auth/register/register.component";
import { adminGuard } from "./core/auth/guards/admin/admin.guard";
import { dataManagerGuard } from "./core/auth/guards/data-manager/data-manager.guard";
import { userGuard } from "./core/auth/guards/user/user.guard";


export const routes: Routes = [
    {
        path:'',
        redirectTo:'home',
        pathMatch:'full'
    },
    {
        path:'home',
        component:HomeComponent,
        title:'Home'
    },
    {
        path:'proficiencies',
        component:ProficiencyListComponent,
        title:'Proficiency list',
        canActivate:[dataManagerGuard]
    },
    {
        path:'proficiencies/deleted',
        component:ProficiencyDeletedListComponent,
        title:'Deleted proficiency list',
        canActivate:[dataManagerGuard]
    },
    {
        path:'proficiencies/create',
        component:CreateProficiencyComponent,
        title:'Create proficiency',
        canActivate:[dataManagerGuard]
    },
    {
        path:'proficiencies/edit/:id',
        component:EditProficiencyComponent,
        title:'Save proficiency',
        canActivate:[dataManagerGuard]
    },
    {
        path:'proficiencies/:id',
        component:ProficiencyDetailsComponent,
        title:'Proficiency details',
        canActivate:[dataManagerGuard]
    },
    {
        path:'classes',
        component:ClassListComponent,
        title:'Class list',
        canActivate:[dataManagerGuard]
    },
    {
        path:'classes/deleted',
        component:ClassDeletedListComponent,
        title:'Deleted class list',
        canActivate:[dataManagerGuard]
    },
    {
        path:'classes/create',
        component:ClassCreateComponent,
        title:'Create class',
        canActivate:[dataManagerGuard]
    },
    {
        path:'classes/edit/:id',
        component:ClassEditComponent,
        title:'Save class',
        canActivate:[dataManagerGuard]
    },
    {
        path:'classes/:id',
        component:ClassDetailsComponent,
        title:'Class details',
        canActivate:[dataManagerGuard]
    },
    {
        path:'spells',
        component:SpellListComponent,
        title:'Spell list',
        canActivate:[dataManagerGuard]
    },
    {
        path:'spells/deleted',
        component:SpellDeletedListComponent,
        title:'Spell list',
        canActivate:[dataManagerGuard]
    },
    {
        path:'spells/create',
        component:SpellCreateComponent,
        title:'Create spell',
        canActivate:[dataManagerGuard]
    },
    {
        path:'spells/edit/:id',
        component:SpellEditComponent,
        title:'Save spell',
        canActivate:[dataManagerGuard]
    },
    {
        path:'spells/:id',
        component:SpellDetailsComponent,
        title:'Spell details',
        canActivate:[dataManagerGuard]
    },
    {
        path:'characters/create',
        component:CharacterCreationComponent,
        title:'Create your character',
        canActivate:[userGuard]
    },
    {
        path:'characters/sheet/:id',
        component:CharacterSheetComponent,
        title:'Your character',
        canActivate:[userGuard]
    },
    {
        path:'characters/deleted',
        component:CharacterDeletedListComponent,
        title:'Your deleted character',
        canActivate:[userGuard]
    },
    {
        path:'characters',
        component:CharacterListComponent,
        title:'Your characters',
        canActivate:[userGuard]
    },
    {
        path:'register',
        component:RegisterComponent,
        title:'New profile'
    },
    {
        path:'login',
        component:LoginComponent,
        title:'Login'
    },
    {
        path:'profile',
        component:ProfileDetailsComponent,
        title:'My profile'
    },
    {
        path:'profile/change/email',
        component:ChangeEmailComponent,
        title:'Chaning email'
    },
    {
        path:'profile/change/username',
        component:ChangeUsernameComponent,
        title:'Changing username'
    },
    {
        path:'profile/change/password',
        component:ChangePasswordComponent,
        title:'Changing password'
    },
    {
        path:"users",
        component:UserListComponent,
        title:'Users',
        canActivate:[adminGuard]
    },
    {
        path:"users/change-role/:id",
        component:ChangeRoleComponent,
        title:'Change role',
        canActivate:[adminGuard]
    },
    {
        path:"**",
        component:PageNotFoundComponent,
        title:'Page Not Found'
    }
];
