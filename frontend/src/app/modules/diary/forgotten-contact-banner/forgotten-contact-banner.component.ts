import { SubSink } from 'subsink';
import { ApiService } from '@services/api.service';
import { MatDialog } from '@angular/material/dialog';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ForgottenContactDialogComponent } from '../forgotten-contact-dialog/forgotten-contact-dialog.component';

@Component({
  selector: 'app-forgotten-contact-banner',
  templateUrl: './forgotten-contact-banner.component.html',
  styleUrls: ['./forgotten-contact-banner.component.scss']
})
export class ForgottenContactBannerComponent implements OnDestroy {
  private subs = new SubSink();

  constructor(
    private dialog: MatDialog,
    private apiService: ApiService) { }

  ngOnDestroy() {
    this.subs.unsubscribe();
  }

  openContactDialog() {
    this.subs.add(this.apiService.getContactPersons()
      .subscribe(contactPersons => this.dialog.open(ForgottenContactDialogComponent, {
        data: {
          contactPersons
        }
      })));
  }
}
