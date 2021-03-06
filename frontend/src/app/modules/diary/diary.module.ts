import { ForgottenContactDialogComponent } from './forgotten-contact-dialog/forgotten-contact-dialog.component';
import { MultipleAutocompleteModule } from '@ui/multiple-autocomplete/multiple-autocomplete.module';
import { ConfirmationDialogModule } from '@ui/confirmation-dialog/confirmation-dialog.module';
import { AlertModule } from '@ui/alert/alert.module';
import { DiaryListItemComponent } from './diary-list-item/diary-list-item.component';
import { RouterModule } from '@angular/router';
import { GroupedDiaryEntriesResolver } from '@resolvers/grouped-diary-entries.resolver';
import { AngularMaterialModule } from '../angular-material/angular-material.module';
import { SymptomsResolver } from '@resolvers/symptoms.resolver';
import { DiaryEntryResolver } from '@resolvers/diary-entry.resolver';
import { DiaryEntryComponent } from './diary-entry/diary-entry.component';
import { DiaryRoutingModule } from './diary-routing.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DiaryComponent } from './diary.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ForgottenContactBannerComponent } from './forgotten-contact-banner/forgotten-contact-banner.component';

const COMPONENTS = [
  DiaryComponent,
  DiaryEntryComponent,
  DiaryListItemComponent,
  ForgottenContactBannerComponent,
  ForgottenContactDialogComponent
];

@NgModule({
  imports: [
    CommonModule,
    DiaryRoutingModule,
    AngularMaterialModule,
    ReactiveFormsModule,
    RouterModule,
    AlertModule,
    ConfirmationDialogModule,
    MultipleAutocompleteModule
  ],
  declarations: [...COMPONENTS],
  entryComponents: [ForgottenContactBannerComponent, ForgottenContactDialogComponent],
  providers:
    [
      DiaryEntryResolver,
      SymptomsResolver,
      GroupedDiaryEntriesResolver,
    ]
})
export class DiaryModule { }
